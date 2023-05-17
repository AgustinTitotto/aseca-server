FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

ARG JAR_FILE=build/libs/aseca-server-0.0.1-SNAPSHOT.jar
# Copy the JAR file to the working directory
COPY ${JAR_FILE} app.jar

# Expose the port on which the app will run
EXPOSE 8080

# Define the command to start the app
CMD ["java", "-jar", "app.jar"]