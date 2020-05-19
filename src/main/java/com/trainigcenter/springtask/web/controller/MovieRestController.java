package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.application.domain.Movie;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.MovieDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.application.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieRestController {

    private static final Logger logger = LogManager.getLogger(MovieRestController.class);

    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieRestController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MovieDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page) {
        List<Movie> allMovies = movieService.getAll(page);
        List<MovieDto> allMovieDto = allMovies.stream()
                                              .map(this::convertToDto)
                                              .collect(Collectors.toList());
        return allMovieDto;
    }

    @GetMapping("/popular")
    public List<Movie> getMostPopular(@RequestParam(value = "raiting", defaultValue = "8") int raiting) {
        List<Movie> movies = movieService.getMostPopularMovies(raiting);
        return movies;
    }

    @GetMapping("/filter")
    public Set<MovieDto> getAllFilterByGenre(@RequestParam("genre") String genre) {
        Set<Movie> movies = movieService.getAllFilterByGenre(genre);
        Set<MovieDto> allMovieDto = movies.stream().map(this::convertToDto).collect(Collectors.toSet());
        return allMovieDto;
    }

    @GetMapping("/{movieId}")
    public MovieDto getMovieById(@PathVariable("movieId") int movieId) {
        Movie movie = movieService.getMovieById(movieId);
        notNullMovie(movie);
        MovieDto movieDto = convertToDto(movie);
        return movieDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMovie(@Valid @RequestBody MovieDto movieDto) {
        movieDto.setId(null);
        Movie movie = convertFromDto(movieDto);
        movieService.addMovie(movie);
    }

    @PutMapping("/{movieId}")
    public MovieDto updateMovie(@PathVariable("movieId") Integer movieId, @Valid @RequestBody MovieDto movieDto) {

        Movie movie = convertFromDto(movieDto);
        movie.setId(movieId);

        movie = movieService.updateMovie(movie);
        notNullMovie(movie);

        return convertToDto(movie);
    }


    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Integer movieId) {

        Movie movie = movieService.getMovieById(movieId);
        notNullMovie(movie);

        movieService.deleteMovie(movie);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error handleBadInput(MethodArgumentNotValidException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Bad request. You should init all movie fields");
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private Error movieNotFound(NotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName());
        return error;
    }

    private void notNullMovie(Movie movie) {
        if (movie == null) {
            throw new NotFoundException("Movie is not exist");
        }
    }

    private MovieDto convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    private Movie convertFromDto(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }
}
