package com.example.superheroes.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
/**
 * Data Transfer Object (DTO) for representing a superhero.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroDto {

    /**
     * The unique identifier for the superhero.
     */
    @NotNull
    private Long id;

    /**
     * The name of the superhero.
     */
    @NotNull
    private String name;
}
