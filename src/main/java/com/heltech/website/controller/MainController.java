package com.heltech.website.controller;

import com.heltech.website.contactUs.ContactForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private EmailService emailService;

    // Existing methods from Step 2
    @GetMapping("/") public String home() { return "index"; }
    @GetMapping("/about") public String about() { return "about"; }
    @GetMapping("/services") public String services() { return "services"; }

    // Display the contact form
    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    // Process the form submission
    @PostMapping("/contact")
    public String submitContactForm(
            @ModelAttribute("contactForm") @Valid ContactForm contactForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "contact"; // Return to the form with validation errors
        }

        try {
            // Send the email
            String emailBody = String.format(
                    "Name: %s\nEmail: %s\n\nMessage:\n%s",
                    contactForm.getName(),
                    contactForm.getEmail(),
                    contactForm.getMessage()
            );
            emailService.sendEmail(contactForm.getEmail(), contactForm.getSubject(), emailBody);
            redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
            return "redirect:/contact?success"; // Redirect to prevent resubmission
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error sending your message. Please try again.");
            return "redirect:/contact?error"; // Redirect with an error parameter
        }
    }
}