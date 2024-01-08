## Dockerfile-prod
##########
FROM openjdk:17-jdk
WORKDIR /app
COPY ./*.jar /app
CMD ["java", "-jar", "app.jar"]
