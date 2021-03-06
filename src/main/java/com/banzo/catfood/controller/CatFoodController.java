package com.banzo.catfood.controller;

import com.banzo.catfood.model.CatFood;
import com.banzo.catfood.service.CatFoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CatFoodController {

    private CatFoodService catFoodService;

    public CatFoodController(CatFoodService catFoodService) {
        this.catFoodService = catFoodService;
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<CatFood>> getCatFoodItems() {

        Iterable<CatFood> catFoodItems = catFoodService.findAll();

        return new ResponseEntity<>(catFoodItems, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CatFood> getCatFood(@PathVariable Long id) {

        Optional<CatFood> catFood = catFoodService.findById(id);

        if (catFood.isEmpty()) {
            throw new RuntimeException("Product with id of " + id + " not found.");
        }

        return new ResponseEntity<>(catFood.get(), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<CatFood> addCatFood(@RequestBody CatFood catFood) {

        catFood.setId(0L);
        CatFood savedCatFood = catFoodService.save(catFood);

        return new ResponseEntity<>(savedCatFood, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<CatFood> updateCatFood(@RequestBody CatFood catFood, @PathVariable Long id) {

        Optional<CatFood> foundCatFood = catFoodService.findById(id);

        if (foundCatFood.isEmpty()) {
            throw new RuntimeException("Product with id of " + id + " not found.");
        } else {
            catFood.setId(id);
            catFoodService.save(catFood);
        }

        return new ResponseEntity<>(catFood, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteCatFood(@PathVariable Long id) {

        catFoodService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/search/findByTypeId")
    public ResponseEntity<Iterable<CatFood>> getCatFoodItemsOfType(@RequestParam("id") Long typeId) {

        Iterable<CatFood> catFoodItemsOfType = catFoodService.findByTypeId(typeId);

        return new ResponseEntity<>(catFoodItemsOfType, HttpStatus.OK);
    }

    @GetMapping("/products/search/findByName")
    public ResponseEntity<Iterable<CatFood>> getCatFoodItemsByName(@RequestParam("name") String name) {

        Iterable<CatFood> foundCatFoodItems = catFoodService.findByName(name);

        return new ResponseEntity<>(foundCatFoodItems, HttpStatus.OK);
    }

    @GetMapping("/products/search/findByRating")
    public ResponseEntity<Iterable<CatFood>> getCatFoodItemsOrderedByRating() {

        Iterable<CatFood> catFoodItems = catFoodService.findAllOrderByRating();

        return new ResponseEntity<>(catFoodItems, HttpStatus.OK);
    }
}
