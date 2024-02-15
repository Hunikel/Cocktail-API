package com.santo.cocktail.repository;

import com.santo.cocktail.models.Cocktail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface CocktailPaginationRepository extends ReactiveSortingRepository<Cocktail, String>{
    Flux<Cocktail> findAllBy(Pageable pageable);
}
