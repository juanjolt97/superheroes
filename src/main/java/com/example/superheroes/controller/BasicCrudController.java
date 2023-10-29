package com.example.superheroes.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BasicCrudController <E,ID>{

    /**
     * Retrieves a page of entities filtered and paginated or filtered by name and paginated.
     *
     * @param name The name to filter entities.
     * @param page The page number.
     * @param size The number of items per page.
     * @return A page of entities.
     */
    ResponseEntity<Page<E>> findAll(String name, Integer page, Integer size, String sortParam);
    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @return The retrieved entity.
     */
    ResponseEntity<E> findById(ID id);
    /**
     * Creates a new entity.
     *
     * @param e The entity to be created.
     * @return The created entity.
     */
    ResponseEntity<E> create(E e);
    /**
     * Updates an existing entity with the given ID.
     *
     * @param e  The updated entity.
     * @param id The ID of the entity to be updated.
     * @return The updated entity.
     */
    ResponseEntity<E> update(E e, ID id);
    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to be deleted.
     */
    void deleteById(ID id);
}
