package com.example.superheroes.dao;

import com.example.superheroes.model.Superhero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for Superhero entities.
 */
@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    /**
     * Search all Superheroes that contains a specified name.
     * @param name The name to search for.
     * @param page Pageable object for pagination.
     * @return Page<Superhero> - Page of matching Superheroes
     */
    Page<Superhero> findAllByNameContainingIgnoreCase(String name, Pageable page);
}
