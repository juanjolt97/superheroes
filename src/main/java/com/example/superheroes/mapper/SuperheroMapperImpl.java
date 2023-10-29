package com.example.superheroes.mapper;

import com.example.superheroes.dto.SuperheroDto;
import com.example.superheroes.model.Superhero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuperheroMapperImpl implements Mapper<SuperheroDto,Superhero>{

    /**
     * Maps a Page of entities to a Page of DTOs.
     *
     * @param e Page of Superhero entities
     * @return Page of SuperheroDto
     */
    @Override
    public Page<SuperheroDto> mapEntityPageToDtoPage(Page<Superhero> e) {
        List<SuperheroDto> dtoBuffer = new ArrayList<>();
        e.forEach(superhero -> dtoBuffer.add(mapToDto(superhero)));
        return new PageImpl<>(dtoBuffer, e.getPageable(), e.getTotalElements());
    }

    /**
     * Maps a SuperheroDto to a Superhero entity.
     *
     * @param d SuperheroDto
     * @return Superhero entity
     */
    @Override
    public Superhero mapToEntity(SuperheroDto d) {
        return new Superhero(d.getId(),d.getName());
    }

    /**
     * Maps a Superhero entity to a SuperheroDto.
     *
     * @param e Superhero entity
     * @return SuperheroDto
     */
    @Override
    public SuperheroDto mapToDto(Superhero e) {
        return new SuperheroDto(e.getId(),e.getName());
    }
}
