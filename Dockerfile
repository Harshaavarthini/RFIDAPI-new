FROM openjdk:17-alpine3.14
COPY ./target/RFIDtrack-0.0.1-SNAPSHOT.jar rfidjar.jar
ENTRYPOINT ["java", "-jar","rfidjar.jar"]


