package com.example.superheroes.service.impl;

import com.example.superheroes.annotation.ExecutionTime;
import com.example.superheroes.dao.SuperheroRepository;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.service.SuperheroService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the SuperheroService interface.
 */
@Service
@AllArgsConstructor
@Api(value = "Superhero Service")
public class SuperheroServiceImpl implements SuperheroService {


    private SuperheroRepository repository;

    /**
     * Find all superheroes.
     *
     * @param pageable The Pageable object for pagination.
     * @return A Page containing superheroes.
     */
    @Override
    @Cacheable(cacheNames = "superheroesCache")
    @ExecutionTime
    public Page<Superhero> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Find superheroes by name.
     *
     * @param name     The name to search for.
     * @param pageable The Pageable object for pagination.
     * @return A Page containing matching superheroes.
     */
    @Override
    @Cacheable(cacheNames = "superheroesCache")
    @ExecutionTime
    public Page<Superhero> findAllByName(String name,Pageable pageable) {
        return repository.findAllByNameContainingIgnoreCase(name,pageable);
    }

    /**
     * Find a superhero by ID.
     *
     * @param id The ID of the superhero to find.
     * @return An Optional containing the superhero if found.
     */
    @Override
    @Cacheable(cacheNames = "superheroesCache")
    @ExecutionTime
    public Optional<Superhero> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Create a superhero.
     *
     * @param superhero The superhero to create.
     * @return An Optional containing the created superhero, or empty if it already exists.
     */
    @Override
    @ExecutionTime
    public Optional<Superhero> create(Superhero superhero) {
        if (repository.findById(superhero.getId()).isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(repository.save(superhero));
        }
    }

    /**
     * Update a superhero by ID.
     *
     * @param id The ID of the superhero to update.
     * @param s  The updated superhero information.
     * @return An Optional containing the updated superhero, or empty if the superhero doesn't exist.
     */
    @Override
    @ExecutionTime
    public Optional<Superhero> update(Long id, Superhero s) {
        return repository.findById(id).map(superhero -> {
            superhero.setId(s.getId());
            superhero.setName(s.getName());
            return repository.save(superhero);
        });
    }

    /**
     * Delete a superhero by ID.
     *
     * @param id The ID of the superhero to delete.
     */
    @Override
    @ExecutionTime
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
