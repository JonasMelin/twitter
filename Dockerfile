FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY twitter4j.properties twitter4j.properties
ENTRYPOINT ["java","-jar","/app.jar"]
