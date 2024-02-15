package com.santo.cocktail.router;

import com.santo.cocktail.handler.CocktailHandler;
import com.santo.cocktail.services.CocktailService;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CocktailRouter {
    private static final String BASE_URL = "/cocktail/";

    @RouterOperations({
            @RouterOperation(path = BASE_URL, beanClass = CocktailService.class, beanMethod = "getAllCocktails"),
            @RouterOperation(path = BASE_URL + "names", beanClass = CocktailService.class, beanMethod = "getAllCocktailNames"),
            @RouterOperation(path = BASE_URL + "{name}", beanClass = CocktailService.class, beanMethod = "getCocktail"),
            @RouterOperation(path = BASE_URL, beanClass = CocktailService.class, beanMethod = "saveCocktail"),
            @RouterOperation(path = BASE_URL, beanClass = CocktailService.class, beanMethod = "updateCocktail"),
            @RouterOperation(path = BASE_URL + "{name}", beanClass = CocktailService.class, beanMethod = "deleteCocktail")})
    @Bean
    public RouterFunction<ServerResponse> cocktailRoute(CocktailHandler handler) {
        return RouterFunctions
                .route(GET(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), handler::getAllCocktails)
                .andRoute(GET(BASE_URL + "names").and(accept(MediaType.APPLICATION_JSON)), handler::getAllCocktailNames)
                .andRoute(GET(BASE_URL + "{name}").and(accept(MediaType.APPLICATION_JSON)), handler::getCocktail)
                .andRoute(PUT(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), handler::saveCocktail)
                .andRoute(PATCH(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), handler::updateCocktail)
                .andRoute(DELETE(BASE_URL + "{name}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteCocktail);
    }

}
