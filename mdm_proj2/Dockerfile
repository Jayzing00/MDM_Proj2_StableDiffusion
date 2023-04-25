FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mdmproj2stablediffusion.jar
ENTRYPOINT ["java", "-jar", "/mdmproj2stablediffusion.jar"]
