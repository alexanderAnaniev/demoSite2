FROM openjdk:19-jdk-alpine3.16
ARG JAR_FILE=target/demo-site-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]