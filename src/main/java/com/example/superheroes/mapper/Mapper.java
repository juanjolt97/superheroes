package com.example.superheroes.mapper;

import org.springframework.data.domain.Page;

/**
 * Interface that maps entities to DTOs and DTOs to entities
 * @param <D> - Input type
 * @param <E> - Output type
 */
public interface Mapper<D,E> {
    /**
     * Maps a DTO to an entity.
     *
     * @param d The DTO to map.
     * @return The corresponding entity.
     */
    E mapToEntity(D d);
    /**
     * Maps an entity to a DTO.
     *
     * @param e The entity to map.
     * @return The corresponding DTO.
     */
    D mapToDto(E e);

    /**
     * Maps a page of entities to a page of DTOs.
     *
     * @param e The page of Entities.
     * @return The corresponding page of DTOs.
     */
    Page<D> mapEntityPageToDtoPage(Page<E> e);

}
