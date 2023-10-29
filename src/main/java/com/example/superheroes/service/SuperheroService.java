package com.example.superheroes.service;

import com.example.superheroes.model.Superhero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface SuperheroService extends GenericService<Superhero> {
    /**
     * Retrieves a page of superheroes based on the provided pagination settings.
     *
     * @param page The pagination settings to determine which page of superheroes to retrieve.
     * @return A page of superheroes according to the provided pagination settings.
     */
    Page<Superhero> findAll(Pageable page);

    /**
     * Retrieves a page of superheroes with a specific name based on the provided pagination settings.
     *
     * @param name The name used to filter superheroes.
     * @param page The pagination settings to determine which page of superheroes to retrieve.
     * @return A page of superheroes with the given name, following the provided pagination settings.
     */
    Page<Superhero> findAllByName(String name, Pageable page);

    /**
     * Retrieves a superhero by their unique identifier (ID).
     *
     * @param id The unique identifier of the superhero to retrieve.
     * @return An optional containing the superhero with the given ID, if found.
     */
    Optional<Superhero> findById(Long id);

    /**
     * Updates an existing superhero identified by their unique identifier (ID).
     *
     * @param id The unique identifier of the superhero to update.
     * @param s The superhero object containing the updated information.
     * @return An optional containing the updated superhero, if the superhero with the given ID is found and successfully updated.
     */
    Optional<Superhero> update(Long id, Superhero s);

    /**
     * Deletes a superhero by their unique identifier (ID).
     *
     * @param id The unique identifier of the superhero to delete.
     */
    void deleteById(Long id);
}
