FROM maven:3.9.9-ibm-semeru-21-jammy AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:24-ea-21-jdk-nanoserver
WORKDIR /app
COPY --from=build /app/target/personal-finance-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
