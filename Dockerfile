# Stage 1: Build the application with a JDK image
FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final, lightweight image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
# Copy the application JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# This is the crucial line to copy your static resources
COPY --from=builder /app/src/main/resources/static ./src/main/resources/static
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]