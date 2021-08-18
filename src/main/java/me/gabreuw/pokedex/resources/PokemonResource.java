package me.gabreuw.pokedex.resources;

import lombok.RequiredArgsConstructor;
import me.gabreuw.pokedex.model.Pokemon;
import me.gabreuw.pokedex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/pokemon")
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
        return POKEMON_REPOSITORY.findById(id)
                .flatMap(existingPokemon -> {
                    Pokemon updatedPokemon = bindPokemon(existingPokemon, pokemon);

                    return POKEMON_REPOSITORY.save(updatedPokemon);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Pokemon bindPokemon(Pokemon fromDataBase, Pokemon fromRequest) {
        fromDataBase.setName(fromRequest.getName());
        fromDataBase.setCategory(fromRequest.getCategory());
        fromDataBase.setSkill(fromRequest.getSkill());
        fromDataBase.setWeight(fromRequest.getWeight());

        return fromDataBase;
    }

}
