package com.santo.cocktail;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Cocktail API",
                version = "1.0"
        ))
public class CocktailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocktailApplication.class, args);
    }

}
