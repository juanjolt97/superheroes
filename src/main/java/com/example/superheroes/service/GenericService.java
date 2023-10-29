package com.example.superheroes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenericService<E> {

    /**
     * Creates an instance in the database for the entity passed as a parameter.
     *
     * @param entity Representation for the record desired to be stored in the database.
     * @return The entity stored in the database (The saved entity will never be null).
     */
    Optional<E> create(E entity);

    /**
     * Updates an instance already stored in the database identified by the id parameter and with the data of the entity parameter.
     *
     * @param id     Identifies a unique instance in the database.
     * @param entity Representation for the record desired to update in the database.
     * @return The entity stored in the database (The saved entity will never be null).
     */
    Optional<E> update(Long id, E entity);

    /**
     * Deletes the entity identified by the parameter id.
     *
     * @param id Identifies a unique instance in the database.
     */
    void deleteById(Long id);

    /**
     * Retrieves the entity identified by the given id.
     *
     * @param id Identifies a unique instance in the database.
     * @return The entity with the given id.
     */
    Optional<E> findById(Long id);

    /**
     * Returns a Page of entities meeting the paging restriction provided in the Pageable object.
     *
     * @param pageable Represents the desired pagination for the query.
     * @return A page of entities.
     */
    Page<E> findAll(Pageable pageable);

    /**
     * Returns a Page of entities findByName meeting the paging restriction provided in the Pageable object.
     *
     * @param name Represents the name of the entity.
     * @param pageable Represents the desired pagination for the query.
     * @return A page of entities.
     */
    Page<E> findAllByName(String name,Pageable pageable);
}
