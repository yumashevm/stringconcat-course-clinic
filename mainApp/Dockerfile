FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} mainApp.jar
ENTRYPOINT ["java","-jar","/mainApp.jar"]