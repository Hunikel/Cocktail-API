package com.santo.cocktail.repository;

import com.santo.cocktail.models.Cocktail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CocktailRepository extends ReactiveCrudRepository<Cocktail, String> {


}
