package com.santo.cocktail.router;

import com.santo.cocktail.handler.IngredientHandler;
import com.santo.cocktail.models.Ingredient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Configuration(proxyBeanMethods = false)
public class IngredientRouter {

    private static final String BASE_URL = "/ingredient/";

    @RouterOperations({
            @RouterOperation(path = BASE_URL + "{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, params = "name",
                    operation = @Operation(operationId = "getAllIngredientsStartingWith",method = "getAllIngredientsStartingWith",
                            parameters = {
                                    @Parameter(name = "name", description = "Ingredient name", required = true)
                            },
                            responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Ingredient.class)))})),
            @RouterOperation(path = BASE_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET,
                    operation = @Operation(operationId = "getAllIngredients",method = "getAllIngredients",
                            responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Ingredient.class)))}))
    })
    @Bean
    public RouterFunction<ServerResponse> ingredientRoute(IngredientHandler handler) {
        return RouterFunctions
                .route(GET(BASE_URL).and(accept(MediaType.APPLICATION_JSON)), handler::getAllIngredients)
                .andRoute(GET(BASE_URL + "{name}").and(accept(MediaType.APPLICATION_JSON)), handler::getAllIngredientsStartingWith);
    }

}
