package me.gabreuw.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PokemonEvent {

    private Long eventId;

    private String eventType;

}
