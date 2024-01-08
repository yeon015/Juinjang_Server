## Dockerfile-prod
##########
FROM openjdk:17-jdk
#EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ./*.jar app.jar
ENTRYPOINT ["java","-jar","/juinjang-0.0.1-SNAPSHOT.jar"]
