# First stage: complete build environment
FROM maven:3.6.3-jdk-11 AS build
WORKDIR /superheroes
COPY pom.xml .
COPY src src
RUN mvn clean package

# Second Stage: Last image
FROM openjdk:11
WORKDIR /superheroes
COPY --from=build /superheroes/target/superheroes-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "superheroes-0.0.1-SNAPSHOT.jar"]