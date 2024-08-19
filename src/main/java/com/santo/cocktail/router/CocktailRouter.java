package com.santo.cocktail.router;

import com.santo.cocktail.handler.CocktailHandler;
import com.santo.cocktail.models.Cocktail;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CocktailRouter {
    private static final String BASE_URL = "/cocktail";

    @RouterOperations({
            @RouterOperation(path = BASE_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, params = "page,size,sort"
                    , operation = @Operation(operationId = "getAllCocktails", description = "Get all cocktails with pagination and sorting",
                    parameters = {
                            @Parameter(name = "page", description = "Page number", required = true),
                            @Parameter(name = "size", description = "Page size", required = true),
                            @Parameter(name = "sort", description = "Sort by")
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation"),
                            @ApiResponse(responseCode = "400", description = "Invalid request"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")
                    })
            ),
            @RouterOperation(path = BASE_URL + "/names", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET
                    , operation = @Operation(operationId = "getAllCocktailNames", description = "Get all cocktail names",

                    responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation"),
                            @ApiResponse(responseCode = "400", description = "Invalid request"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")
                    })
            ),
            @RouterOperation(path = BASE_URL + "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, params = "name"
                    , operation = @Operation(operationId = "getCocktail", description = "Get a cocktail by name"
                    , parameters = {
                    @Parameter(name = "name", description = "Cocktail name", required = true)
            }
                    , responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "404", description = "Cocktail not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
            ),
            @RouterOperation(path = BASE_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, params = "cocktail"
                    , operation = @Operation(operationId = "saveCocktail", description = "Save a cocktail",
                    parameters = {
                            @Parameter(name = "cocktail", description = "Cocktail object", required = true, content = @Content(schema = @Schema(implementation = Cocktail.class)))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "successful operation"),
                            @ApiResponse(responseCode = "400", description = "Invalid request"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")
                    })
            ),
            @RouterOperation(path = BASE_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PATCH, params = "cocktail"
                    , operation = @Operation(operationId = "updateCocktail", description = "Update a cocktail",
                    parameters = {
                            @Parameter(name = "cocktail", description = "Cocktail object", required = true, content = @Content(schema = @Schema(implementation = Cocktail.class)))
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation"),
                            @ApiResponse(responseCode = "400", description = "Invalid request"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")
                    })
            ),
            @RouterOperation(path = BASE_URL + "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, params = "name"
                    , operation = @Operation(operationId = "deleteCocktail", description = "Delete a cocktail by name",
                    parameters = {
                            @Parameter(name = "name", description = "Cocktail name", required = true)
                    },
                    responses = {
                            @ApiResponse(responseCode = "204", description = "successful operation"),
                            @ApiResponse(responseCode = "404", description = "Cocktail not found"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
            ),
            @RouterOperation(path = BASE_URL + "/count", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, operation = @Operation(operationId = "getCount", description = "Get the total count of all cocktails",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error")
                    })
            )
    })
    @Bean
    public RouterFunction<ServerResponse> cocktailRoute(CocktailHandler handler) {
        return RouterFunctions
                .route(GET(BASE_URL + "/getAllCocktails").and(accept(MediaType.APPLICATION_JSON)), handler::getAllCocktails)
                .andRoute(GET(BASE_URL + "/names").and(accept(MediaType.APPLICATION_JSON)), handler::getAllCocktailNames)
                .andRoute(GET(BASE_URL + "/{name}").and(accept(MediaType.APPLICATION_JSON)), handler::getCocktail)
                .andRoute(POST(BASE_URL + "/").and(accept(MediaType.APPLICATION_JSON)), handler::saveCocktail)
                .andRoute(PUT(BASE_URL + "/").and(accept(MediaType.APPLICATION_JSON)), handler::updateCocktail)
                .andRoute(DELETE(BASE_URL + "/{name}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteCocktail);
    }

}
