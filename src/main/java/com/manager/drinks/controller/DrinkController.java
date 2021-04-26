package com.manager.drinks.controller;

import com.manager.drinks.models.Drinks;
import com.manager.drinks.models.DrinkUpdateRequest;
import com.manager.drinks.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DrinkController {
    
    @Autowired
    DrinkRepository drinkRepository;
    
    @RequestMapping("/")
    public String index() {
        return "Welcome to the Drink Recipe Manager demo app!";
    }
    
    @PostMapping("/drinks")
    public Drinks addDrinkPost(@RequestBody Drinks newDrink) {
        return drinkRepository.save(newDrink);
    }
    
    @GetMapping("/drinks/{id}")
    public Optional<Drinks> getDrink(@PathVariable String id) {
        if (drinkRepository.existsById(id)) {
            return drinkRepository.findById(id);
        } else
            return Optional.empty();
    }
    
    @DeleteMapping("/drinks/{id}")
    public void deleteById(@PathVariable String id) {
        drinkRepository.deleteById(id);
    }
    
    @PutMapping("/drinks/{idToBeUpdated}")
    public String updateDrink(@PathVariable String idToBeUpdated, @RequestBody DrinkUpdateRequest drinkUpdateRequest) {
        
        Optional<Drinks> mayBeDrink = drinkRepository.findById(idToBeUpdated)
                .map(drinks -> drinkRepository
                        .save(Drinks
                                .builder()
                                .id(idToBeUpdated)
                                .name(drinkUpdateRequest.getName())
                                .ingredients(drinkUpdateRequest.getIngredients())
                                .alcohol(drinks.getAlcohol())
                                .build())
                );
        if (mayBeDrink.isPresent()) {
            return "SUCCESS: Drink recipe has been updated!";
        } else {
            return "ERROR: Drink recipe does not exist.";
        }
    }
}
