FROM openjdk:21
WORKDIR /my-project
COPY build/libs/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]