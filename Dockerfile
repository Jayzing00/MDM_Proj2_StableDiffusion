# Use the official OpenJDK image as the base image
FROM openjdk:8-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file created by the Maven build into the container
COPY target/mdm_proj2-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]
