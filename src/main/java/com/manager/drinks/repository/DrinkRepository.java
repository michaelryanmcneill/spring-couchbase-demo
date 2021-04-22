package com.manager.drinks.repository;

import com.manager.drinks.models.Drinks;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DrinkRepository extends CrudRepository<Drinks, String> {
    
    Drinks findByAlcohol(String alcohol);
}