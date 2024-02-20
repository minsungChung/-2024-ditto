FROM openjdk:17-jdk
LABEL authors="minsung"
COPY /ditto-module-batch/build/libs/ditto-module-batch-0.0.1-SNAPSHOT.jar ditto.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "ditto.jar"]