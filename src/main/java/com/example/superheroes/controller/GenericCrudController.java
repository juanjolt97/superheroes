package com.example.superheroes.controller;

import com.example.superheroes.mapper.Mapper;
import com.example.superheroes.service.GenericService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * An abstract controller for CRUD (Create, Read, Update, Delete) operations for a specific entity type and DTO type.
 *
 * @param <E> The entity type.
 * @param <D> The DTO (Data Transfer Object) type.
 */

abstract class GenericCrudController<E, D> implements BasicCrudController<D, Long>
{
    /**
     * Get the service responsible for handling the CRUD operations for the entity.
     *
     * @return A GenericService for the entity type.
     */
    abstract GenericService<E> getService() ;
    /**
     * Get the mapper responsible for mapping between the entity and DTO types.
     *
     * @return A Mapper for mapping between the DTO and entity types.
     */
    abstract Mapper<D,E> getMapper();

    /**
     * Retrieves a list of entities, optionally filtered by name, with paging and sorting options.
     *
     * @param name     (Optional) The name by which to filter the entities.
     * @param page     (Optional) The page number for pagination.
     * @param size     (Optional) The number of items per page for pagination.
     * @param sortParam (Optional) The sorting parameter, in the format "property,direction."
     * @return A page of DTO objects.
     */
    @GetMapping
    @Override
    @ApiOperation(value = "Find entities", notes = "Get a list of entities optionally filtered by name")
    public ResponseEntity<Page<D>> findAll(@RequestParam(value = "name", required = false)String name,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size,
                                                      @RequestParam(value = "sort", required = false) String sortParam) {
        Sort sort = Sort.unsorted();
        if (sortParam != null && !sortParam.isEmpty()) {
            String[] parts = sortParam.split(",");
            if (parts.length == 2) {
                String property = parts[0];
                String direction = parts[1];
                sort= Sort.by(Sort.Direction.fromString(direction), property);
            }
        }
        if(name!=null){
            return ResponseEntity.ok(getMapper().mapEntityPageToDtoPage(getService().findAllByName(name, PageRequest.of(page != null ? page : 0, size != null ? size : 20, sort))));
        }else{
            return ResponseEntity.ok(getMapper().mapEntityPageToDtoPage(getService().findAll(PageRequest.of(page != null ? page : 0, size != null ? size : 20, sort))));
        }
    }

    /**
     * Retrieves a entity by their unique ID.
     *
     * @param id The unique ID of the entity to retrieve.
     * @return A DTO object representing the entity.
     * @throws IllegalArgumentException If the entity with the given ID does not exist.
     */
    @GetMapping("/{id}")
    @Override
    @ApiOperation(value = "Find entity by ID", notes = "Get a entity by their unique ID")
    public ResponseEntity<D> findById(@PathVariable("id")Long id) {
        return ResponseEntity.ok(getMapper().mapToDto(getService().findById(id).orElseThrow(() -> new IllegalArgumentException("Can't find a entity" +
                " if that id: "+id+" it does not exist"))));
    }

    /**
     * Creates a new entity based on the provided DTO.
     *
     * @param d The DTO representing the new entity.
     * @return A DTO representing the created entity.
     * @throws NoSuchElementException If the provided entity is not present.
     */
    @PostMapping
    @Override
    @ApiOperation(value = "Create entity", notes = "Create a new entity")
    public ResponseEntity<D> create(@RequestBody D d) {
        Optional<E> result = getService().create(getMapper().mapToEntity(d));
        if (result.isPresent()) {
            return ResponseEntity.ok(getMapper().mapToDto(result.get()));
        } else {
            throw new NoSuchElementException("Can't create entity if entity is not present");
        }
    }

    /**
     * Updates an existing entity by their ID based on the provided DTO.
     *
     * @param d The DTO representing the updated entity.
     * @param id           The ID of the entity to update.
     * @return A DTO representing the updated entity.
     * @throws NoSuchElementException If the entity is not present.
     */
    @PatchMapping(value = "/{id}")
    @Override
    @ApiOperation(value = "Update entity", notes = "Update an existing entity by their ID")
    public ResponseEntity<D> update(@RequestBody D d, @PathVariable("id")Long id) {
        Optional<E> result = getService().update(id, getMapper().mapToEntity(d));
        if (result.isPresent()) {
            return ResponseEntity.ok(getMapper().mapToDto(result.get()));
        } else {
            throw new NoSuchElementException("Can't update entity if entity is not present");
        }

    }

    /**
     * Deletes a entity by their unique ID.
     *
     * @param id The unique ID of the entity to delete.
     */
    @DeleteMapping("/{id}")
    @Override
    @ApiOperation(value = "Delete entity", notes = "Delete a entity by their unique ID")
    public void deleteById(@PathVariable("id")Long id) {
        getService().deleteById(id);
    }
}
