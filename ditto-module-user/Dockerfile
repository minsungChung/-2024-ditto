FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-user-0.0.1-SNAPSHOT.jar user.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8087
#ENTRYPOINT ["java", "-jar", "user.jar"]