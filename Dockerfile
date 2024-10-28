FROM gradle:8.9.0-jdk21 AS build
RUN mkdir /app
COPY ./build/libs/by.arro.grace-light-server-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]