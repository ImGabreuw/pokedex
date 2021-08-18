package me.gabreuw.pokedex.repositories;

import me.gabreuw.pokedex.model.Pokemon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PokemonRepository extends ReactiveMongoRepository<Pokemon, String> {}