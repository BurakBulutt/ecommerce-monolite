FROM maven:3-amazoncorretto-17-alpine AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/target/ecommerce-monolite-1.0.0.jar .

ENTRYPOINT ["java", "-jar", "ecommerce-monolite-1.0.0.jar"]
