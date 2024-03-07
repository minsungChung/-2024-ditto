FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "gateway.jar"]