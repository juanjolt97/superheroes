package com.example.superheroes.controller;

import com.example.superheroes.dto.SuperheroDto;
import com.example.superheroes.mapper.Mapper;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.service.GenericService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * The SuperheroController class is responsible for handling HTTP requests related to superheroes.
 */
@RestController
@RequestMapping("/superhero")
@AllArgsConstructor
@Api(value = "Superhero Controller")
public class SuperheroController extends GenericCrudController<Superhero,SuperheroDto>{

    private GenericService<Superhero> service;
    private Mapper<SuperheroDto, Superhero> mapper;


    @Override
    GenericService<Superhero> getService() {
        return service;
    }

    @Override
    Mapper<SuperheroDto, Superhero> getMapper() {
        return mapper;
    }
}
