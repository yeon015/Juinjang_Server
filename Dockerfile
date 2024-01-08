# ## Dockerfile-prod
# ##########
FROM openjdk:17-jdk
#EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
RUN pwd
# RUN ls build*
RUN ls *.jar
COPY ./*.jar app.jar
ENTRYPOINT ["java","-jar","./app.jar"]

# FROM openjdk:17-jdk
# #EXPOSE 8080
# RUN pwd
# RUN ls ./build/libs
# COPY ./build/libs/*.jar /app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
