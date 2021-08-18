package me.gabreuw.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pokemon {

    @Id
    private String id;

    private String name;

    private String category;

    private String skill;

    private Double weight;

}
