FROM openjdk:latest
COPY /target/auth_server.jar auth_server.jar
ENTRYPOINT ["java", "-jar", "auth_server.jar"]
EXPOSE 8090