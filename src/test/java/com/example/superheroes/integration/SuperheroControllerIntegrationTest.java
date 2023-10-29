package com.example.superheroes.integration;

import com.example.superheroes.SuperheroesApplication;
import com.example.superheroes.dao.SuperheroRepository;

import com.example.superheroes.model.Superhero;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SuperheroesApplication.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SuperheroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SuperheroRepository repository;

    @Before
    public void SetUp(){
        Superhero superhero1 = new Superhero(1L, "Spiderman");
        Superhero superhero2 = new Superhero(2L, "Superman");
        Superhero superhero3 = new Superhero(3L, "Manolito el fuerte");

        repository.save(superhero1);
        repository.save(superhero2);
        repository.save(superhero3);
    }

    @After
    public void tearDown(){
        repository.deleteAll();
    }
    @Test
    public void givenSuperheroesWithNoUser_whenFindAllSuperheroes_thenStatus401() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/superhero")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"USER", "ADMIN"})
    public void givenSuperheroes_whenFindAllSuperheroes_thenStatus200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/superhero")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", CoreMatchers.is("Spiderman")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name", CoreMatchers.is("Superman")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name", CoreMatchers.is("Manolito el fuerte")));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"USER", "ADMIN"})
    public void givenSuperheroes_whenFindById_thenStatus200() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/superhero/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"USER", "ADMIN"})
    public void givenSuperheroes_whenCreateSuperhero_thenStatus200() throws Exception{
        Superhero superhero4 = new Superhero(4L, "Dr. Strange");
        String jsonString = new ObjectMapper().writeValueAsString(superhero4);
        mockMvc.perform(MockMvcRequestBuilders.post("/superhero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Dr. Strange")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"USER", "ADMIN"})
    public void givenSuperhero_whenUpdateSuperhero_thenStatus200() throws Exception{
        Superhero superhero1 = new Superhero(1L,"WonderWoman");
        String jsonString = new ObjectMapper().writeValueAsString(superhero1);
        mockMvc.perform(MockMvcRequestBuilders.patch("/superhero/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("WonderWoman")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"USER", "ADMIN"})
    public void givenSuperhero_whenDeleteSuperhero_thenStatus200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/superhero/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
