package com.example.superheroes.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * The Superhero class represents a superhero entity with an ID and a name.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Superhero {
    /**
     * The unique identifier (ID) of the superhero.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    /**
     * The name of the superhero.
     */
    @NotNull
    private String name;

}
