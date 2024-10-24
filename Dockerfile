# Use a Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests


# Use OpenJDK 17 image to run the Spring Boot app
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/ShopBackEnd-1.0.jar ShopBackEnd.jar

# Expose the application's port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ShopBackEnd.jar"]