package io.tpd.superheroes.controller;

import io.tpd.superheroes.domain.SuperHero;
import io.tpd.superheroes.exceptions.NonAllowedHeroException;
import io.tpd.superheroes.repository.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/superheroes")
public final class SuperHeroController {

    @Value("${superheroes.features.endpointAllowed}")
    private Boolean endpointAllowed;  // TODO next, how do we add dynamic toggles that do not require the instance to restart? review SpringCloud, Unleash etc.

    private final SuperHeroRepository superHeroRepository;

    @Autowired
    public SuperHeroController(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @GetMapping("/{id}")
    public SuperHero getSuperHeroById(@PathVariable int id) {
        if (endpointAllowed == false) {
            // As an extra step we can add more check in code
            throw new NonAllowedHeroException("YOU CANT DO THIS - YOU HAVE BEEN REPORTED!");
        }
        return superHeroRepository.getSuperHero(id);
    }

//    @ConditionalOnProperty(name = "superheroes.features.v2Enabled", havingValue = "true")
//    @GetMapping("/v2/{id}") // test how ConditionalOnProperty works
//    public SuperHero getSuperHeroById405(@PathVariable int id) {
//        return superHeroRepository.getSuperHero(id);
//    }

    @GetMapping
    public Optional<SuperHero> getSuperHeroByHeroName(@RequestParam("name") String heroName) {
        return superHeroRepository.getSuperHero(heroName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewSuperHero(@RequestBody @Valid SuperHero superHero) {
        superHeroRepository.saveSuperHero(superHero);
    }

}
