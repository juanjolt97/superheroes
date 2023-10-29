package com.example.superheroes.unitary;

import com.example.superheroes.dao.SuperheroRepository;
import com.example.superheroes.model.Superhero;
import com.example.superheroes.service.impl.SuperheroServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class SuperheroServiceImplTest {

    @Mock
    private SuperheroRepository repository;

    @InjectMocks
    private SuperheroServiceImpl service;

    @Autowired
    private CacheManager cacheManager;
    @Test
    public void givenListSuperhero_whenFindAllSuperhero_thenReturnOk() {
        // Configuration of test data
        Pageable pageable = PageRequest.of(0, 10);
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1L, "Spiderman"));
        superheroes.add(new Superhero(2L, "Superman"));

        //Call repository to return all superheroes
        Mockito.when(repository.findAll(pageable)).thenReturn(new PageImpl<>(superheroes));

        //Call service to return all superheroes, then the second time must return from cache not repository
        Page<Superhero> result = service.findAll(pageable);

        //Verify that repository was called just 1 time
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);

        //Verify that the list size of superheroes created in configuration is the same that the size returned from service.
        Assertions.assertEquals(superheroes.size(), result.getTotalElements());
        //Verify that the list of superheroes created in configuration is the same that the superheroes returned from service.
        Assertions.assertEquals(superheroes, result.getContent());

    }

    @Test
    public void givenListSuperheroByName_whenFindAllSuperheroByName_thenReturnOk() {
        // Configuration of test data
        String name="man";
        Pageable pageable = PageRequest.of(0, 10);
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1L, "Spiderman"));
        superheroes.add(new Superhero(2L, "Superman"));
        superheroes.add(new Superhero(3L, "Manolito el fuerte"));

        //Call repository to return all superheroes by name
        Mockito.when(repository.findAllByNameContainingIgnoreCase(name,pageable)).thenReturn(new PageImpl<>(superheroes));

        //Call service to return all superheroes by name, then the second time must return from cache not repository
        Page<Superhero> result = service.findAllByName(name,pageable);

        //Verify that repository was called just 1 time
        Mockito.verify(repository, Mockito.times(1)).findAllByNameContainingIgnoreCase(name,pageable);

        //Verify that the list size of superheroes created in configuration is the same that the size returned from service.
        Assertions.assertEquals(superheroes.size(), result.getTotalElements());
        //Verify that the list of superheroes created in configuration is the same that the superheroes returned from service.
        Assertions.assertEquals(superheroes, result.getContent());

    }

    @Test
    public void givenSuperhero_whenFindById_thenReturnOk(){
        // Configuration of test data
        Long superheroId = 1L;
        Superhero superhero = new Superhero(superheroId, "Spiderman");

        //Configure repository to return a superhero when call findById
        Mockito.when(repository.findById(superheroId)).thenReturn(Optional.of(superhero));

        //Call the method findById from service(Should read from repository)
        Optional<Superhero> result1 = service.findById(superheroId);
        //Call the method findById from service(Should read from cache)
        Optional<Superhero> result2 = service.findById(superheroId);

        //Verify the result returned from 2 calls to service methods are the same
        Assert.isTrue(result1.equals(result2), "The result1 must be equal to result2");

        // Also, we can verify if the cache is not null
        Assert.notNull(cacheManager.getCache("superheroesCache"), "Cache 'superheroesCache' mustn't null");
    }

}
