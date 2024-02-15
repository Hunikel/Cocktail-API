package com.santo.cocktail.router;

import com.santo.cocktail.handler.IngredientHandler;
import com.santo.cocktail.models.Ingredient;
import com.santo.cocktail.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class IngredientRouter {

    private static final String BASE_URL = "/ingredient/";

    @RouterOperations({
            @RouterOperation(path = BASE_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = IngredientService.class, beanMethod = "getAllIngredients",
                    operation = @Operation(operationId = "getAllIngredients", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Ingredient.class)))})),
            @RouterOperation(path = BASE_URL + "{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = IngredientService.class, beanMethod = "getAllIngredientsStartingWith",
                    operation = @Operation(operationId = "getAllIngredientsStartingWith", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Ingredient.class)))}))
    })
    @Bean
    public RouterFunction<ServerResponse> ingredientRoute(IngredientHandler handler) {
        return RouterFunctions
                .route(GET(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), handler::getAllIngredients)
                .andRoute(GET(BASE_URL + "{name}").and(accept(MediaType.APPLICATION_JSON)), handler::getAllIngredientsStartingWith);
    }
//    @Bean
//    @RouterOperation(path = BASE_URL, method = RequestMethod.GET, beanClass = IngredientHandler.class, beanMethod = "getAllIngredients")
//    public RouterFunction<ServerResponse> routeForAllIngredients(IngredientHandler ingredientHandler) {
//        return RouterFunctions
//                .route(GET(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), ingredientHandler::getAllIngredients);
//    }
//
//    @Bean
//    @RouterOperation(path = BASE_URL + "{name}", method = RequestMethod.GET, beanClass = IngredientHandler.class, beanMethod = "getIngredient")
//    public RouterFunction<ServerResponse> routeToGetIngredientsStartingWith(IngredientHandler ingredientHandler) {
//        return RouterFunctions
//                .route(GET(BASE_URL + "{name}").and(accept(MediaType.APPLICATION_JSON)), ingredientHandler::getAllIngredientsStartingWith);
//    }


}
