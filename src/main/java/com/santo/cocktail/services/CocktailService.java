package com.santo.cocktail.services;

import com.santo.cocktail.dto.CocktailDTO;
import com.santo.cocktail.exception.ResourceNotFoundException;
import com.santo.cocktail.mapper.CocktailMapper;
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
    private final CocktailMapper cocktailMapper;

    public Mono<Page<CocktailDTO>> getAllCocktails(Pageable pageable) {
        return cocktailPaginationRepository.findAllBy(pageable)
                .map(cocktailMapper::toCocktailDTO)
                .collectList()
                .zipWith(cocktailRepository.count())
                .map(c -> new PageImpl<>(c.getT1(), pageable, c.getT2()));
    }

    public Flux<CocktailDTO> getCocktails() {
        return cocktailRepository.findAll()
                .map(cocktailMapper::toCocktailDTO);
    }

    public Mono<CocktailDTO> getCocktail(String name) {
        return cocktailRepository.findById(name)
                .map(cocktailMapper::toCocktailDTO)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cocktail not found with name: " + name)));
    }

    public Mono<CocktailDTO> saveCocktail(CocktailDTO cocktailDTO) {
        Cocktail cocktail = cocktailMapper.toCocktail(cocktailDTO);
        return cocktailRepository.save(cocktail)
                .map(cocktailMapper::toCocktailDTO);
    }

    public Mono<CocktailDTO> updateCocktail(CocktailDTO cocktailDTO) {
        return cocktailRepository.findById(cocktailDTO.getName())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cocktail not found with name: " + cocktailDTO.getName())))
                .flatMap(existingCocktail -> {
                    Cocktail updatedCocktail = cocktailMapper.toCocktail(cocktailDTO);
                    return cocktailRepository.save(updatedCocktail);
                })
                .map(cocktailMapper::toCocktailDTO);
    }

    public Mono<Void> deleteCocktail(String name) {
        return cocktailRepository.deleteById(name);
    }

    public Flux<String> getAllCocktailNames() {
        return cocktailRepository.findAll()
                .map(Cocktail::getName);
    }

}
