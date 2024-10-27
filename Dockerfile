FROM openjdk:17-alpine

WORKDIR /app

COPY target/*.jar ecommerce_app.jar

ENTRYPOINT ["java", "-jar", "ecommerce_app.jar"]
