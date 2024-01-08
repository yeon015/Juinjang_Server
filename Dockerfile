## Dockerfile-prod
##########
FROM openjdk:17-jdk
#EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ./*.jar app.jar
RUN ls -l *
ENTRYPOINT ["java","-jar","./app.jar"]


