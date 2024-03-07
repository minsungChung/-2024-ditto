FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-user-0.0.1-SNAPSHOT.jar user.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "user.jar"]