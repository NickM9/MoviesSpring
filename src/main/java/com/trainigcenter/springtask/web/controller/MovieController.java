package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.MovieDto;
import com.trainigcenter.springtask.web.exception.ForbiddenException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private static final Logger logger = LogManager.getLogger(MovieController.class);

    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MovieDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "2") int size,
                                 @RequestParam(value = "rating", defaultValue = "8") int rating) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        return movieService.getAll(page, size)
                           .stream()
                           .map(this::convertToDto)
                           .collect(Collectors.toList());
    }

    @GetMapping("/popular")
    public List<MovieDto> getMostPopular(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "2") int size,
                                         @RequestParam(value = "rating", defaultValue = "8") int rating) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        return movieService.getByRating(rating, page, size)
                           .stream()
                           .map(this::convertToDto)
                           .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<MovieDto> getAllFilterByGenre(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "2") int size,
                                              @RequestParam(value = "genre", required = true) String genre) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }
        genre = genre.toLowerCase().replaceAll("-", " ");

        List<Movie> movies = movieService.getAllByGenre(genre, page, size);
        return movies.stream()
                     .map(this::convertToDto)
                     .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable("id") int id) {
        Movie movie = Optional.ofNullable(movieService.getById(id))
                              .orElseThrow(() -> new NotFoundException("Movie id:" + id + " nor found"));

        return convertToDto(movie);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto saveMovie(@Valid @RequestBody MovieDto movieDto) {
        movieDto.setId(null);
        Movie movie = movieService.add(convertFromDto(movieDto));

        return convertToDto(movie);
    }

    @PutMapping("/{id}")
    public MovieDto updateMovie(@PathVariable("id") Integer id, @Valid @RequestBody MovieDto movieDto) {

        Movie movie = convertFromDto(movieDto);
        movie.setId(id);

        movie = Optional.ofNullable(movieService.update(movie))
                        .orElseThrow(() -> new NotFoundException("Movie id:" + id + " nor found"));

        return convertToDto(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") Integer id) {

        Movie movie = Optional.ofNullable(movieService.getById(id))
                              .orElseThrow(() -> new NotFoundException("Movie id:" + id + " nor found"));

        movieService.delete(movie);
    }


    @ExceptionHandler({NotFoundException.class, ForbiddenException.class})
    private ResponseEntity<Error> exceptionHandler(WebException e) {
        Error error = new Error(e.getStatus().value(), e.getMessage());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error notValidArgumentHandler(Exception e) {
        return new Error(HttpStatus.BAD_REQUEST.value(), "You need to initialize all fields");
    }

    private MovieDto convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    private Movie convertFromDto(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }
}
