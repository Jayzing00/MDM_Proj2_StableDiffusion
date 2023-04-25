FROM openjdk:8-jdk

WORKDIR /app

COPY mdm_proj2/target/mdm_proj2-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
