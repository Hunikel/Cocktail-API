package com.santo.cocktail.services;

import com.santo.cocktail.models.Cocktail;
import com.santo.cocktail.repository.CocktailPaginationRepository;
import com.santo.cocktail.repository.CocktailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CocktailService {

    private final CocktailPaginationRepository cocktailPaginationRepository;
    private final CocktailRepository cocktailRepository;

    public Mono<Page<Cocktail>> getAllCocktails(Pageable pageable) {
        return cocktailPaginationRepository.findAllBy(pageable)
                .collectList()
                .zipWith(cocktailRepository.count())
                .map(c -> new PageImpl<>(c.getT1(), pageable, c.getT2()));
    }

    public Flux<Cocktail> getCocktails() {
        return cocktailRepository.findAll();
    }

    public Mono<Cocktail> getCocktail(String name) {
        return cocktailRepository.findById(name);
    }

    public Mono<Cocktail> saveCocktail(Cocktail cocktail) {
        return cocktailRepository.save(cocktail);
    }


    public Mono<Cocktail> updateCocktail(Cocktail cocktail) {
        return cocktailRepository.save(cocktail);
    }

    public Mono<Void> deleteCocktail(String name) {
        return cocktailRepository.deleteById(name);
    }

    public Flux<String> getAllCocktailNames() {
        return cocktailRepository.findAll().map(Cocktail::getName);
    }

}
