FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-activity-0.0.1-SNAPSHOT.jar activity.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "activity.jar"]