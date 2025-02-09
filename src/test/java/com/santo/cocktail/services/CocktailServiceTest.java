package com.santo.cocktail.services;

import com.santo.cocktail.dto.CocktailDTO;
import com.santo.cocktail.exception.ResourceNotFoundException;
import com.santo.cocktail.mapper.CocktailMapper;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

class CocktailServiceTest {

    @Mock
    private CocktailPaginationRepository cocktailPaginationRepository;

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailMapper cocktailMapper;

    @InjectMocks
    private CocktailService cocktailService;

    private List<Cocktail> cocktails;
    private List<CocktailDTO> cocktailDTOs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock data
        cocktails = List.of(
                new Cocktail("Mojito", "Classic", "A refreshing cocktail", "Long description", "image_url", List.of()),
                new Cocktail("Margarita", "Classic", "A tangy cocktail", "Long description", "image_url", List.of())
        );

        cocktailDTOs = List.of(
                new CocktailDTO("Mojito", "Classic", "A refreshing cocktail", "Long description", "image_url", List.of()),
                new CocktailDTO("Margarita", "Classic", "A tangy cocktail", "Long description", "image_url", List.of())
        );
    }

    @Test
    void testGetAllCocktails() {
        // Mock data
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cocktail> cocktailPage = new PageImpl<>(cocktails, pageable, cocktails.size());

        // Mock repository behavior
        when(cocktailPaginationRepository.findAllBy(pageable)).thenReturn(Flux.fromIterable(cocktails));
        when(cocktailRepository.count()).thenReturn(Mono.just((long) cocktails.size()));

        // Mock mapper behavior
        when(cocktailMapper.toCocktailDTO(any(Cocktail.class))).thenAnswer(invocation -> {
            Cocktail cocktail = invocation.getArgument(0);
            return new CocktailDTO(cocktail.getName(), cocktail.getCategory(), cocktail.getShortDescription(),
                    cocktail.getLongDescription(), cocktail.getImageUrl(), List.of());
        });

        // Call the service method
        Mono<Page<CocktailDTO>> result = cocktailService.getAllCocktails(pageable);

        // Verify the result
        Assertions.assertNotNull(result.block());
        Assertions.assertEquals(cocktails.size(), Objects.requireNonNull(result.block()).getTotalElements());
        Assertions.assertEquals(cocktailDTOs.getFirst().getName(), Objects.requireNonNull(result.block()).getContent().getFirst().getName());

        // Verify interactions
        verify(cocktailPaginationRepository, times(1)).findAllBy(pageable);
        verify(cocktailRepository, times(1)).count();
        verify(cocktailMapper, times(cocktails.size())).toCocktailDTO(any(Cocktail.class));
    }

    @Test
    void testGetCocktail() {
        // Mock data
        Cocktail cocktail = cocktails.getFirst();
        CocktailDTO cocktailDTO = cocktailDTOs.getFirst();

        // Mock repository behavior
        when(cocktailRepository.findById("Mojito")).thenReturn(Mono.just(cocktail));

        // Mock mapper behavior
        when(cocktailMapper.toCocktailDTO(cocktail)).thenReturn(cocktailDTO);

        // Call the service method
        Mono<CocktailDTO> result = cocktailService.getCocktail("Mojito");

        // Verify the result
        Assertions.assertNotNull(result.block());
        Assertions.assertEquals(cocktailDTO.getName(), Objects.requireNonNull(result.block()).getName());

        // Verify interactions
        verify(cocktailRepository, times(1)).findById("Mojito");
        verify(cocktailMapper, times(1)).toCocktailDTO(cocktail);
    }

    @Test
    void testGetCocktail_NotFound() {
        // Mock repository behavior
        when(cocktailRepository.findById("Unknown")).thenReturn(Mono.empty());

        // Call the service method and expect an exception
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cocktailService.getCocktail("Unknown").block();
        });

        // Verify interactions
        verify(cocktailRepository, times(1)).findById("Unknown");
    }

    @Test
    void testSaveCocktail() {
        // Mock data
        CocktailDTO cocktailDTO = cocktailDTOs.getFirst();
        Cocktail cocktail = cocktails.getFirst();

        // Mock mapper behavior
        when(cocktailMapper.toCocktail(cocktailDTO)).thenReturn(cocktail);

        // Mock repository behavior
        when(cocktailRepository.save(cocktail)).thenReturn(Mono.just(cocktail));
        when(cocktailMapper.toCocktailDTO(cocktail)).thenReturn(cocktailDTO);

        // Call the service method
        Mono<CocktailDTO> result = cocktailService.saveCocktail(cocktailDTO);

        // Verify the result
        Assertions.assertNotNull(result.block());
        Assertions.assertEquals(cocktailDTO.getName(), Objects.requireNonNull(result.block()).getName());

        // Verify interactions
        verify(cocktailMapper, times(1)).toCocktail(cocktailDTO);
        verify(cocktailRepository, times(1)).save(cocktail);
        verify(cocktailMapper, times(1)).toCocktailDTO(cocktail);
    }

    @Test
    void testUpdateCocktail() {
        // Mock data
        CocktailDTO cocktailDTO = cocktailDTOs.getFirst();
        Cocktail cocktail = cocktails.getFirst();

        // Mock repository behavior
        when(cocktailRepository.findById("Mojito")).thenReturn(Mono.just(cocktail));
        when(cocktailRepository.save(cocktail)).thenReturn(Mono.just(cocktail));

        // Mock mapper behavior
        when(cocktailMapper.toCocktail(cocktailDTO)).thenReturn(cocktail);
        when(cocktailMapper.toCocktailDTO(cocktail)).thenReturn(cocktailDTO);

        // Call the service method
        Mono<CocktailDTO> result = cocktailService.updateCocktail(cocktailDTO);

        // Verify the result
        Assertions.assertNotNull(result.block());
        Assertions.assertEquals(cocktailDTO.getName(), Objects.requireNonNull(result.block()).getName());

        // Verify interactions
        verify(cocktailRepository, times(1)).findById("Mojito");
        verify(cocktailRepository, times(1)).save(cocktail);
        verify(cocktailMapper, times(1)).toCocktail(cocktailDTO);
        verify(cocktailMapper, times(1)).toCocktailDTO(cocktail);
    }

    @Test
    void testUpdateCocktail_NotFound() {
        // Mock data
        CocktailDTO cocktailDTO = cocktailDTOs.get(0);

        // Mock repository behavior
        when(cocktailRepository.findById("Unknown")).thenReturn(Mono.empty());

        // Call the service method and expect an exception
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cocktailService.updateCocktail(cocktailDTO).block();
        });

        // Verify interactions
        verify(cocktailRepository, times(1)).findById("Unknown");
    }

    @Test
    void testDeleteCocktail() {
        // Mock repository behavior
        when(cocktailRepository.deleteById("Mojito")).thenReturn(Mono.empty());

        // Call the service method
        Mono<Void> result = cocktailService.deleteCocktail("Mojito");

        // Verify the result
        Assertions.assertNull(result.block());

        // Verify interactions
        verify(cocktailRepository, times(1)).deleteById("Mojito");
    }

    @Test
    void testGetAllCocktailNames() {
        // Mock repository behavior
        when(cocktailRepository.findAll()).thenReturn(Flux.fromIterable(cocktails));

        // Call the service method
        Flux<String> result = cocktailService.getAllCocktailNames();

        // Verify the result
        Assertions.assertEquals(2, Objects.requireNonNull(result.collectList().block()).size());

        // Verify interactions
        verify(cocktailRepository, times(1)).findAll();
    }
}
