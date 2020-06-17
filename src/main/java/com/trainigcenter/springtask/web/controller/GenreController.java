package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<GenreDto> getAll() {
        return genreService.getAll()
                           .stream()
                           .map(this::convertToDto)
                           .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable("id") int id) {
        return convertToDto(genreService.getById(id).orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto create(@Valid @RequestBody GenreDto genreDto) {
        genreDto.setId(null);
        Genre genre = genreService.save(convertFromDto(genreDto));
        return convertToDto(genre);
    }

    @PutMapping("/{id}")
    public GenreDto update(@PathVariable("id") int id,
                           @Valid @RequestBody GenreDto genreDto) {
        genreDto.setId(id);
        Genre genre = genreService.save(convertFromDto(genreDto));
        return convertToDto(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        genreService.delete(id);
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertFromDto(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }
}
