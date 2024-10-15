FROM openjdk:17-alpine

WORKDIR /app

COPY target/ecommerce-monolite-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ecommerce-monolite-0.0.1-SNAPSHOT.jar"]
