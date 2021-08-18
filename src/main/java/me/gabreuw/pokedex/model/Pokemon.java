package me.gabreuw.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pokemon implements Serializable {

    @Id
    private String id;

    private String name;

    private String category;

    private String skill;

    private Double weight;

}
