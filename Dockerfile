FROM openjdk:17-jdk
LABEL authors="minsung"
COPY build/libs/ditto-0.0.1-SNAPSHOT.jar ditto.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "ditto.jar"]