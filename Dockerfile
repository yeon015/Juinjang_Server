## Dockerfile-prod
##########

FROM openjdk:17-jdk
#EXPOSE 8080
ARG JAR_FILE=build/libs/*-0.0.1-SNAPSHOT.jar
COPY ./*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
