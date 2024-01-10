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
#FROM openjdk:17-jdk
#EXPOSE 8080
#ARG JAR_FILE=build/libs/*.jar
#COPY ./*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]


## try~
## Dockerfile-prod
##########
FROM gradle:7.6-jdk17-alpine as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# APP
FROM openjdk:17.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]



# FROM openjdk:17-jdk
# ARG JAR_FILE=build/libs/*.jar
# COPY ./*.jar /app.jar
# RUN chmod +x /app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# FROM openjdk:17-jdk
# WORKDIR /app
# ARG JAR_FILE=build/libs/*.jar
# COPY ./*.jar /app/app.jar
# RUN chmod +x /app/app.jar
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]
