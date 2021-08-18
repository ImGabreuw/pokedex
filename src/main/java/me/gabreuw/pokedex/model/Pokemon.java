package me.gabreuw.pokedex.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Pokemon {

    @Id
    private String id;

    private String name;

    private String category;

    private String skill;

    private Double weight;

}
