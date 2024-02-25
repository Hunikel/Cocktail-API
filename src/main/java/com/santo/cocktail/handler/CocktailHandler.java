package com.santo.cocktail.handler;

import com.santo.cocktail.models.Cocktail;
import com.santo.cocktail.services.CocktailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CocktailHandler {
    private final CocktailService cocktailService;

    public Mono<ServerResponse> getAllCocktails(ServerRequest request) {
        Pageable pageable = PageRequest.of(Integer.parseInt(request.queryParam("page").orElse("0")),
                Integer.parseInt(request.queryParam("size").orElse("10")),
                Sort.by("name").ascending());
        log.info("getAllCocktails called request: {}", request);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cocktailService.getAllCocktails(pageable), Cocktail.class);
    }
    public Mono<ServerResponse> getAllCocktailNames(ServerRequest request) {
        log.info("getAllCocktailNames request: {}", request);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cocktailService.getAllCocktailNames(), String.class);
    }
    public Mono<ServerResponse> getCocktail(ServerRequest request) {
        log.info("getCocktail request: {}", request);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cocktailService.getCocktail(request.pathVariable("name")), Cocktail.class);
    }

    public Mono<ServerResponse> saveCocktail(ServerRequest request) {
        log.info("saveCocktail request: {}", request);
        return request.bodyToMono(Cocktail.class)
                .flatMap(cocktail -> cocktailService.saveCocktail(cocktail)
                        .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build())
                );
    }

    public Mono<ServerResponse> updateCocktail(ServerRequest request) {
        log.info("updateCocktail request: {}", request);
        return request.bodyToMono(Cocktail.class)
                .flatMap(cocktail -> cocktailService.updateCocktail(cocktail)
                        .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build())
                );
    }

    public Mono<ServerResponse> deleteCocktail(ServerRequest request) {
        log.info("deleteCocktail request: {}", request);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cocktailService.deleteCocktail(request.pathVariable("name")), Void.class);
    }

}
