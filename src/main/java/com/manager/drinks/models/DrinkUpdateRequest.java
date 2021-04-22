package com.manager.drinks.models;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DrinkUpdateRequest {
    
    @Size(min = 1)
    @Field
    String name;
    
    @Field
    List<String> ingredients;
}
