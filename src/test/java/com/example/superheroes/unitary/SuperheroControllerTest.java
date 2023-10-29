package com.example.superheroes.unitary;

import com.example.superheroes.controller.SuperheroController;
import com.example.superheroes.dto.SuperheroDto;
import com.example.superheroes.mapper.Mapper;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.service.GenericService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class SuperheroControllerTest {

    private SuperheroController controller;
    private GenericService<Superhero> service;
    private Mapper<SuperheroDto, Superhero> mapper;

    @Before
    public void setUp() {
        //Configure controller with Mock classes
        service = Mockito.mock(GenericService.class);
        mapper = Mockito.mock(Mapper.class);
        controller = new SuperheroController(service, mapper);
    }

    @Test
    public void givenSuperheroes_whenFindAllSuperheroes_thenStatus200() {
        // Configure example data to test
        Superhero superhero1 = new Superhero(1L, "Spiderman");
        Superhero superhero2 = new Superhero(2L, "Superman");
        Superhero superhero3 = new Superhero(3L, "Manolito el fuerte");
        Page<Superhero> superheroPage = new PageImpl<>(List.of(superhero1, superhero2, superhero3));

        // Configure a dto example data to test
        SuperheroDto superheroDto1 = new SuperheroDto(1L, "Spiderman");
        SuperheroDto superheroDto2 = new SuperheroDto(2L, "Superman");
        SuperheroDto superheroDto3 = new SuperheroDto(3L, "Manolito el fuerte");
        Page<SuperheroDto> superheroDtoPage = new PageImpl<>(List.of(superheroDto1, superheroDto2, superheroDto3));

        // Configure the behaviour for the service and mapper
        Mockito.when(service.findAll(Mockito.any())).thenReturn(superheroPage);
        Mockito.when(mapper.mapEntityPageToDtoPage(superheroPage)).thenReturn(superheroDtoPage);

        // Call the method
        ResponseEntity<Page<SuperheroDto>> result = controller.findAll(null, 0, 20, "name,asc");

        // Verify the response
        Assertions.assertEquals(ResponseEntity.ok(superheroDtoPage), result);

        // Verify the service methods and mapper are called
        Mockito.verify(service, Mockito.times(1)).findAll(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1)).mapEntityPageToDtoPage(superheroPage);
    }

    @Test
    public void givenSuperhero_whenFindById_thenStatus200() {

        // Example of entity superhero and DTO superhero
        Superhero superhero = new Superhero(1L, "Spiderman");
        SuperheroDto superheroDto = new SuperheroDto(1L, "Spiderman");

        // Configure the behaviour waited for the service and mapper
        Mockito.when(service.findById(1L)).thenReturn(Optional.of(superhero));
        Mockito.when(mapper.mapToDto(superhero)).thenReturn(superheroDto);

        // Call the method
        ResponseEntity<SuperheroDto> result = controller.findById(1L);

        // Verify the response
        Assertions.assertEquals(ResponseEntity.ok(superheroDto), result);

        //Verify that the service and mapper are called
        Mockito.verify(service, Mockito.times(1)).findById(1L);
        Mockito.verify(mapper, Mockito.times(1)).mapToDto(superhero);
    }

    @Test
    public void givenNonExistentSuperhero_whenFindById_thenStatus404() {

        // Configure the behaviour for the service when superhero is not found
        Mockito.when(service.findById(1L)).thenReturn(Optional.empty());

        // Call the method and verify that exception is called
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.findById(1L));

        // Verify that service is called
        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    @Test
    public void givenSuperhero_whenCreateSuperhero_thenStatus200() {
        // Define a simulated superhero and its corresponding DTO
        Superhero superhero = new Superhero(1L, "Spiderman");
        SuperheroDto superheroDto = new SuperheroDto(1L, "Spiderman");

        // Set the expected behavior for the service and the mapper
        Mockito.when(mapper.mapToEntity(superheroDto)).thenReturn(superhero);
        Mockito.when(service.create(superhero)).thenReturn(Optional.of(superhero));
        Mockito.when(mapper.mapToDto(superhero)).thenReturn(superheroDto);

        // Make the method call
        ResponseEntity<SuperheroDto> result = controller.create(superheroDto);

        // Verify that the response is as expected
        org.junit.Assert.assertEquals(ResponseEntity.ok(superheroDto), result);

        // Verify that the service and mapper methods were called as expected
        Mockito.verify(mapper, Mockito.times(1)).mapToEntity(superheroDto);
        Mockito.verify(service, Mockito.times(1)).create(superhero);
        Mockito.verify(mapper, Mockito.times(1)).mapToDto(superhero);
    }

    @Test
    public void givenExistingSuperhero_whenCreateSuperhero_thenStatus400() {
        // Define a simulated superhero and its corresponding DTO
        Superhero superhero = new Superhero(1L, "Spiderman");
        SuperheroDto superheroDto = new SuperheroDto(1L, "Spiderman");

        // Set the expected behavior for the service (simulate that the hero already exists)
        Mockito.when(mapper.mapToEntity(superheroDto)).thenReturn(superhero);
        Mockito.when(service.create(superhero)).thenReturn(Optional.empty());

        // Make the method call and verify that it throws a NoSuchElementException
        org.junit.Assert.assertThrows(NoSuchElementException.class, () -> controller.create(superheroDto));

        // Verify that the service and mapper methods were called as expected
        Mockito.verify(mapper, Mockito.times(1)).mapToEntity(superheroDto);
        Mockito.verify(service, Mockito.times(1)).create(superhero);
    }

}
