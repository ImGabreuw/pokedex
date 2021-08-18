package me.gabreuw.pokedex.configuration;

import lombok.RequiredArgsConstructor;
import me.gabreuw.pokedex.model.Pokemon;
import me.gabreuw.pokedex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Initialization implements CommandLineRunner {

    private final ReactiveMongoOperations MONGO_OPERATIONS;
    private final PokemonRepository POKEMON_REPOSITORY;

    @Override
    public void run(String... args) throws Exception {
        Flux<Pokemon> data = Flux.just(
                        new Pokemon(
                                null,
                                "Blastoise",
                                "Marisco",
                                "Torrente",
                                9.0
                        ),
                        new Pokemon(
                                null,
                                "Caterpie",
                                "Minhoca",
                                "Poeira do excuto",
                                2.08
                        ),
                        new Pokemon(
                                null,
                                "Bulbassauro",
                                "Semente",
                                "Grandeza",
                                10.08
                        )
                )
                .flatMap(POKEMON_REPOSITORY::save);

        data.thenMany(POKEMON_REPOSITORY.findAll())
                .subscribe(System.out::println);
    }

}
