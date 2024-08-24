package com.examplesoft.ecommercemonolite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
public class EcommerceMonoliteApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceMonoliteApplication.class, args);
    }

}
