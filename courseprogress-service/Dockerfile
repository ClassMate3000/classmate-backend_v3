#----------------------------------------------------------------
# Stage 1: Build the application - Use a base JDK image
#----------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle files first (better caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the Spring Boot JAR
RUN ./gradlew bootJar --no-daemon


#----------------------------------------------------------------
# Stage 2: Run the application
#----------------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR built in the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port defined in task-service's application.properties
EXPOSE 8081

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]