package com.heltech.website.contactUs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactForm {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Subject cannot be empty.")
    private String subject;

    @NotBlank(message = "Message cannot be empty.")
    @Size(min = 10, max = 500, message = "Message must be between 10 and 500 characters.")
    private String message;

    // Getters and Setters (you can use Lombok for this to reduce boilerplate code)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}