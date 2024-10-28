FROM gradle:8.9.0-jdk21 AS build
RUN mkdir /app
RUN gradlew shadowJar
COPY ./build/libs/by.arro.grace-light-server-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]