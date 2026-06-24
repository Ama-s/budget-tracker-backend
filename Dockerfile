# Use Java 17 base image
FROM eclipse-temurin:21-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy gradle files
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Build the application (skip tests for faster build)
RUN chmod +x gradlew && ./gradlew build -x test


# Use a smaller image for runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8085

# Run the application
ENTRYPOINT ["java", "-Dserver.address=0.0.0.0", "-Dserver.port=8085", "-jar", "app.jar"]