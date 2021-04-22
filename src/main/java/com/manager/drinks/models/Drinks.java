package com.manager.drinks.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@Document
public class Drinks {
    
    @Id
    final String id;
    
    @Size(min=1)
    @NotNull
    @Field
    String name;
    
    @NotNull
    @Field
    String alcohol;
    
    @Field
    List<String> ingredients;

}
