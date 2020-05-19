package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.application.domain.Genre;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.GenreDto;
import com.trainigcenter.springtask.web.exception.ForbiddenException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.application.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreRestController {

    private static final Logger logger = LogManager.getLogger(GenreRestController.class);

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreRestController(GenreService genreService, ModelMapper modelMapper) {
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<GenreDto> getAll() {
        Set<Genre> allGenres = genreService.getAll();
        Set<GenreDto> allGenreDto = allGenres.stream()
                                             .map(this::converToDto)
                                             .collect(Collectors.toSet());
        return allGenreDto;
    }

    @GetMapping("/{genreId}")
    public GenreDto getGenreById(@PathVariable("genreId") int genreId) {
        Genre genre = genreService.getGenreById(genreId);

        notNullGenre(genre);

        GenreDto genreDto = converToDto(genre);
        return genreDto;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveGenre(@Valid @RequestBody GenreDto genreDto) {

        genreDto.setId(null);
        genreDto.setName(mapGenreName(genreDto.getName()));

        Genre genre = convertFromDto(genreDto);
        genreService.addGenre(genre);
    }

    @PutMapping("/{genreId}")
    public GenreDto updateGenre(@PathVariable("genreId") Integer genreId, @Valid @RequestBody GenreDto genreDto) {

        Genre genre = convertFromDto(genreDto);
        genre.setId(genreId);
        genre.setName(mapGenreName(genre.getName()));

        genre = genreService.updateGenre(genre);
        notNullGenre(genre);

        return converToDto(genre);
    }

    @DeleteMapping("/{genreId}")
    public void deleteGenre(@PathVariable("genreId") Integer genreId) {

        Genre genre = genreService.getGenreByIdWithMovies(genreId);
        notNullGenre(genre);

        boolean isDeleted = genreService.deleteGenre(genre);

        if (!isDeleted) {
            throw new ForbiddenException(genre.getName());
        }

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Error genreNotFound(NotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName() + " not found");
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error handleBadInput(MethodArgumentNotValidException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Bad request. You should init the genre name");
        return error;
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private Error handleCanNotDeleted(ForbiddenException e) {
        Error error = new Error(HttpStatus.FORBIDDEN.value(), e.getName() + " take part in movie");
        return error;
    }

    private void notNullGenre(Genre genre) {
        if (genre == null) {
            throw new NotFoundException("Genre not exist");
        }
    }

    private GenreDto converToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    private Genre convertFromDto(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }

    private String mapGenreName(String genreName) {
        genreName = genreName.toLowerCase();
        genreName = genreName.replaceAll("-", " ");
        return genreName;
    }

}
