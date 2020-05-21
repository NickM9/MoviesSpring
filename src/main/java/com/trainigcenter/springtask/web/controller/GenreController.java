package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.GenreDto;
import com.trainigcenter.springtask.web.exception.ForbiddenException;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private static final Logger logger = LogManager.getLogger(GenreController.class);

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

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable("id") int id) {
        Genre genre = Optional.ofNullable(genreService.getById(id))
                              .orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));

        return convertToDto(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto saveGenre(@Valid @RequestBody GenreDto genreDto) {
        genreDto.setId(null);
        genreDto.setName(mapGenreName(genreDto.getName()));

        Genre genre = genreService.add(convertFromDto(genreDto));
        return convertToDto(genre);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto updateGenre(@PathVariable("id") Integer id,
                                @Valid @RequestBody GenreDto genreDto) {

        Genre genre = convertFromDto(genreDto);
        genre.setId(id);
        genre.setName(mapGenreName(genre.getName()));

        genre = Optional.ofNullable(genreService.update(genre))
                        .orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));

        return convertToDto(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") Integer id) {

        Genre genre = Optional.ofNullable(genreService.getByIdWithMovies(id))
                              .orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));

        boolean isDeleted = genreService.delete(genre);

        if (!isDeleted) {
            throw new ForbiddenException(genre.getName() + " is take part in movies");
        }

    }

    @ExceptionHandler({NotFoundException.class, ForbiddenException.class, MethodNotAllowedException.class})
    private ResponseEntity<Error> exceptionHandler(WebException e) {
        Error error = new Error(e.getStatus().value(), e.getMessage());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error notValidArgumentHandler(Exception e) {
        return new Error(HttpStatus.BAD_REQUEST.value(), "You need to initialize all fields");
    }

    private GenreDto convertToDto(Genre genre) {
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
