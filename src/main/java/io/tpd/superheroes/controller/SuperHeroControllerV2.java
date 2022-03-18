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

// Can only be applied at controller level, not mappings.  If we want to turn on/off different mappings,
// we need to have different controllers, if using the annotation.
@ConditionalOnProperty(name = "superheroes.features.v2Enabled", havingValue = "true") // Causes a 404
@RestController
@RequestMapping("/superheroes/v2")
public final class SuperHeroControllerV2 {

    private final SuperHeroRepository superHeroRepository;

    @Autowired
    public SuperHeroControllerV2(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @GetMapping("/{id}")
    public SuperHero getSuperHeroById(@PathVariable int id) {
        return superHeroRepository.getSuperHero(id);
    }

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
