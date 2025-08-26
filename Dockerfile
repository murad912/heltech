# Stage 1: Build the application with a JDK image
# This stage is for compiling your Java code
FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final, lightweight image
# This stage contains only the JRE and your packaged application
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]