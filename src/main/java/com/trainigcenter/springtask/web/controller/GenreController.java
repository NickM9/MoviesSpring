package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.dto.GenreDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreController(GenreService genreService, ModelMapper modelMapper) {
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<GenreDto> getAll() {
        List<Genre> allGenres = genreService.getAll();
        return allGenres.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertFromDto(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }

}
