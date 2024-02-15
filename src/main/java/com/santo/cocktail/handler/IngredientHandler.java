package com.santo.cocktail.handler;

import com.santo.cocktail.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IngredientHandler {

    private final IngredientService ingredientService;

    public Mono<ServerResponse> getAllIngredients(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.getAllIngredient(), String.class);
    }

    public Mono<ServerResponse> getAllIngredientsStartingWith(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.getAllIngredientsStartingWith(request.pathVariable("name")), String.class);
    }
}
