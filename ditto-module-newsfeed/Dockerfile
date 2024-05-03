FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-newsfeed-0.0.1-SNAPSHOT.jar newsfeed.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "newsfeed.jar"]