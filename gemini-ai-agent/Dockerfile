FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-jar", "/app.jar"]