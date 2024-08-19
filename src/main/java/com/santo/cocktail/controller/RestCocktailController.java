//package com.santo.cocktail.controller;
//
//import com.santo.cocktail.dto.CocktailCountDTO;
//import com.santo.cocktail.models.Cocktail;
//import com.santo.cocktail.services.CocktailService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/cocktail")
//@RequiredArgsConstructor
//@Slf4j
//public class RestCocktailController {
//
//    private final CocktailService cocktailService;
//
//    @GetMapping("/")
//    public ResponseEntity<Page<Cocktail>> getAllCocktails(@RequestParam(defaultValue = "0") int page,
//                                                          @RequestParam(defaultValue = "10") int size) {
//        log.info("Get all cocktails from page {} and size {}",page,size);
//        Pageable pageable = PageRequest.of(page, size);
//        return ResponseEntity.ok(cocktailService.getAllCocktails(pageable).block());
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<Cocktail> getCocktail(@PathVariable String name) {
//        log.info("Get cocktail with name {}",name);
//        return cocktailService.getCocktail(name)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .block();
//    }
//
//    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Cocktail> saveCocktail(@RequestBody Cocktail cocktail) {
//        log.info("Save new cocktail {}",cocktail);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(cocktailService.saveCocktail(cocktail).block());
//    }
//
//    @PutMapping(path = "/")
//    public ResponseEntity<Cocktail> updateCocktail(@RequestBody Cocktail cocktail) {
//        log.info("Update cocktail {}",cocktail);
//        return ResponseEntity.ok(cocktailService.updateCocktail(cocktail).block());
//    }
//
//    @DeleteMapping("/{name}")
//    public ResponseEntity<Void> deleteCocktail(@PathVariable String name) {
//        log.info("Delete cocktail with name {}",name);
//        cocktailService.deleteCocktail(name).block();
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/names")
//    public ResponseEntity<List<String>> getAllCocktailNames() {
//        log.info("Get all cocktails names");
//        return ResponseEntity.ok(cocktailService.getAllCocktailNames().collectList().block());
//    }
//
//    @GetMapping("/count")
//    public ResponseEntity<Long> getTotalCount() {
//        log.info("Get total count of cocktails");
//        return ResponseEntity.ok(cocktailService.getTotalCount().map(CocktailCountDTO::getCount).block());
//    }
//}
