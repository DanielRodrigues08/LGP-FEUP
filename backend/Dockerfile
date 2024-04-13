# Build stage
FROM gradle:8.7.0-jdk17-alpine as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test

# Runtime stage
FROM bellsoft/liberica-openjdk-alpine:17.0.7 AS runtime
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
# Expose the port that the Spring Boot application listens on
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]