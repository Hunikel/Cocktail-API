package com.santo.cocktail.services;

import com.santo.cocktail.models.Cocktail;
import com.santo.cocktail.repository.CocktailPaginationRepository;
import com.santo.cocktail.repository.CocktailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

class CocktailServiceTest {
    @Mock
    CocktailPaginationRepository cocktailPaginationRepository;
    @Mock
    CocktailRepository cocktailRepository;
    @InjectMocks
    CocktailService cocktailService;

    private List<Cocktail> cocktails;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cocktails = List.of(new Cocktail(), new Cocktail(), new Cocktail(), new Cocktail(), new Cocktail());
    }

    @Test
    void testGetAllCocktails() {
        Mono<Long> count = Mono.just(5L);
        when(cocktailRepository.count()).thenReturn(count);
        Page page = mock(Page.class);
        when(page.getContent()).thenReturn(cocktails);
        when(page.getTotalElements()).thenReturn(5L);

        Pageable pageable = mock(Pageable.class);
        when(pageable.getPageSize()).thenReturn(10);
        when(pageable.getPageNumber()).thenReturn(0);
        when(pageable.getSort()).thenReturn(null);

        when(cocktailPaginationRepository.findAllBy(pageable)).thenReturn(Flux.fromIterable(cocktails));
        Mono<Page<Cocktail>> result = cocktailService.getAllCocktails(pageable);
        Assertions.assertEquals(page.getTotalElements(), Objects.requireNonNull(result.block()).getTotalElements());
    }

    @Test
    void testGetCocktail() {
        when(cocktailRepository.findById(anyString())).thenReturn(Mono.just(new Cocktail()));

        Mono<Cocktail> result = cocktailService.getCocktail("name");
        Assertions.assertEquals(new Cocktail(), result.block());
    }

    @Test
    void testSaveCocktail() {
        when(cocktailRepository.save(any())).thenReturn(Mono.just(new Cocktail()));

        Mono<Cocktail> result = cocktailService.saveCocktail(new Cocktail());
        Assertions.assertEquals(new Cocktail(), result.block());
    }

    @Test
    void testUpdateCocktail() {
        when(cocktailRepository.save(any())).thenReturn(Mono.just(new Cocktail()));

        Mono<Cocktail> result = cocktailService.updateCocktail(new Cocktail());
        Assertions.assertEquals(new Cocktail(), result.block());
    }

    @Test
    void testDeleteCocktail() {
        when(cocktailRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = cocktailService.deleteCocktail("name");
        Assertions.assertNull(result.block());
    }

    @Test
    void testGetAllCocktailNames() {
        cocktails.forEach(cocktail -> cocktail.setName("name" + cocktails.indexOf(cocktail)));
        when(cocktailRepository.findAll()).thenReturn(Flux.fromIterable(cocktails));

        Flux<String> result = cocktailService.getAllCocktailNames();
        Assertions.assertEquals(5, result.collectList().block().size());
    }
}
