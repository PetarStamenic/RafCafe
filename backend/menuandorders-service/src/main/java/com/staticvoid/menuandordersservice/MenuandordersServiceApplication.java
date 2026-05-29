package com.staticvoid.menuandordersservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "RafCafe Menu and Orders Service"
        )
)
public class MenuandordersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenuandordersServiceApplication.class, args);
    }
}