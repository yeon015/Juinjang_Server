# # ## Dockerfile-prod
# # ##########
# FROM openjdk:17-jdk
# #EXPOSE 8080
# ARG JAR_FILE=build/libs/*.jar
# RUN pwd
# # RUN ls build*
# RUN ls *.jar
# COPY ./*.jar app.jar
# ENTRYPOINT ["java","-jar","./app.jar"]

## Dockerfile-prod
##########
FROM openjdk:17
#EXPOSE 8080
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_PATH}/juinjang-0.0.1-SNAPSHOT.jar ${JAR_PATH}/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

