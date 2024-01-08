## Dockerfile-prod
##########
FROM openjdk:17-jdk
WORKDIR /app
COPY target/app.jar /app
CMD ["java", "-jar", "app.jar"]
