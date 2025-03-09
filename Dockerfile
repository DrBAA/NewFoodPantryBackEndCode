# # CREATE A DOCKER IMAGE

# # Use the official Maven image with OpenJDK 21
# FROM maven:3.9.9-eclipse-temurin-21 AS build

# # Set the working directory
# WORKDIR /app

# # Copy the pom.xml file and download dependencies
# COPY pom.xml .
# RUN mvn dependency:go-offline

# # Copy the source code and build the application
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Use the official OpenJDK 21 slim image for the runtime
# FROM openjdk:21-jdk-slim

# # Set the working directory
# WORKDIR /app

# # Copy the built JAR file from the build stage
# COPY --from=build /app/target/MyFoodPantryAPI-0.0.1-SNAPSHOT.jar /app/myfoodpantryapi.jar

# # Expose the application port
# EXPOSE 8080

# # Run the application
# ENTRYPOINT ["java", "-jar", "/app/myfoodpantryapi.jar"]
