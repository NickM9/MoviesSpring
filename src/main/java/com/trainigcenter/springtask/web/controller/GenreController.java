package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.dto.GenreDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/genres")
public class GenreController {

    private static final Logger logger = LogManager.getLogger(GenreController.class);

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<GenreDto> getAll() {
        List<Genre> allGenres = genreService.getAll();
        return allGenres.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable("id") int id) {
        Genre genre = genreService.getById(id).orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));
        return convertToDto(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto saveGenre(@Valid @RequestBody GenreDto genreDto) {
        Genre genre = genreService.create(convertFromDto(genreDto));
        return convertToDto(genre);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable("id") int id,
                                @Valid @RequestBody GenreDto genreDto) {

        Genre genre = convertFromDto(genreDto);
        return convertToDto(genreService.update(genre, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable("id") Integer id) {
        Genre genre = genreService.getByIdWithMovies(id).orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));
        genreService.delete(genre.getId());
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertFromDto(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }

}
