FROM openjdk:17-jdk
LABEL authors="minsung"
COPY ./build/libs/ditto-module-stock-0.0.1-SNAPSHOT.jar stock.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "stock.jar"]