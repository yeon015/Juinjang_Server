## Dockerfile-prod
##########
FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
