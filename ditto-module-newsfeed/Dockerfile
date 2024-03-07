FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-newsfeed-0.0.1-SNAPSHOT.jar newsfeed.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "newsfeed.jar"]