#FROM maven:3.9-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:17-jre
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
# --- STAGE 1: Build Stage ---
# Use JDK 21 to compile the source code
FROM eclipse-temurin:21-jdk-alpine AS build

# Set the working directory for build commands
WORKDIR /workspace/app

# Copy all project files into the container
COPY . .

# Grant execute permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application and skip unit tests for faster deployment
RUN ./gradlew clean build -x test

# --- STAGE 2: Runtime Stage ---
# Use a lightweight JRE (Runtime) for the final production image
FROM eclipse-temurin:21-jre-alpine

# Set a clean directory for the application to run
WORKDIR /app

# Copy only the compiled JAR file from the 'build' stage
# This keeps the final image small by discarding source code and build tools
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Inform the container environment that the app listens on port 8080
EXPOSE 8080

# Command to launch the Java application
ENTRYPOINT ["java", "-jar", "app.jar"]
