package com.letscode.shoppingcartsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class ShoppingcartsapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingcartsapiApplication.class, args);
    }

}
