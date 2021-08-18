package me.gabreuw.pokedex.resources;

import lombok.RequiredArgsConstructor;
import me.gabreuw.pokedex.model.Pokemon;
import me.gabreuw.pokedex.model.PokemonEvent;
import me.gabreuw.pokedex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping(path = "/pokemons")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PokemonResource {

    private final PokemonRepository POKEMON_REPOSITORY;

    @GetMapping
    public Flux<Pokemon> findAll() {
        return POKEMON_REPOSITORY.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Pokemon>> findById(@PathVariable String id) {
        // if-else funcional
        return POKEMON_REPOSITORY
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<Pokemon> save(@RequestBody Pokemon pokemon) {
        return POKEMON_REPOSITORY.save(pokemon);
    }

    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<Pokemon>> update(
            @PathVariable String id,
            @RequestBody Pokemon pokemon
    ) {
        return POKEMON_REPOSITORY
                .findById(id)
                .flatMap(existingPokemon -> {
                    Pokemon updatedPokemon = bindPokemon(existingPokemon, pokemon);

                    return POKEMON_REPOSITORY.save(updatedPokemon);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return POKEMON_REPOSITORY.deleteById(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return POKEMON_REPOSITORY.deleteAll();
    }

    @GetMapping(
            path = "/events",
            produces = TEXT_EVENT_STREAM_VALUE
    )
    public Flux<PokemonEvent> findPokemonEvents() {
        return Flux
                .interval(Duration.ofSeconds(5L))
                .map(value -> new PokemonEvent(value, "pokemonzitos"));
    }

    private Pokemon bindPokemon(Pokemon fromDataBase, Pokemon fromRequest) {
        fromDataBase.setName(fromRequest.getName());
        fromDataBase.setCategory(fromRequest.getCategory());
        fromDataBase.setSkill(fromRequest.getSkill());
        fromDataBase.setWeight(fromRequest.getWeight());

        return fromDataBase;
    }

}
