FROM gradle:8.9-jdk21 AS build
WORKDIR /app
COPY ./build.gradle ./gradlew ./gradlew.bat ./gradle.properties ./settings.gradle /app/
COPY gradle /app/gradle
COPY src /app/src
RUN ./gradlew clean build -x test

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
