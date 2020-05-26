package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.util.Pagination;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.dto.MovieDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public Pagination<MovieDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "2") int size) {

        System.out.println("get all");
        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        Pagination<Movie> moviePagination = movieService.getAll(page, size);

        if (page > moviePagination.getMaxPage()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return convertToPaginationDto(moviePagination);
    }


    @GetMapping(params = "genreId")
    public Pagination<MovieDto> getAllFilterByGenre(@RequestParam(value = "genre") int genreId,
                                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "2") int size
                                                    ) {

        System.out.println("Get filter");

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        Pagination<Movie> moviePagination = movieService.getAllByGenre(genreId, page, size);

        if (page > moviePagination.getMaxPage()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return convertToPaginationDto(moviePagination);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable("id") int id) {
        Movie movie = movieService.getById(id).orElseThrow(() -> new NotFoundException("Movie id:" + id + " not found"));

        return convertToDto(movie);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto saveMovie(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = movieService.create(convertFromDto(movieDto));
        return convertToDto(movie);
    }

    @PutMapping("/{id}")
    public MovieDto updateMovie(@PathVariable("id") int id,
                                @Valid @RequestBody MovieDto movieDto) {

        Movie movie = convertFromDto(movieDto);
        return convertToDto(movieService.update(movie, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") Integer id) {

        Movie movie = movieService.getById(id).orElseThrow(() -> new NotFoundException("Movie id:" + id + " not found"));
        movieService.delete(movie.getId());
    }

    private MovieDto convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    private Movie convertFromDto(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    private Pagination<MovieDto> convertToPaginationDto(Pagination<Movie> pagination) {
        List<MovieDto> movies = pagination.getObjects()
                                          .stream()
                                          .map(this::convertToDto)
                                          .collect(Collectors.toList());

        return new Pagination<>(pagination.getMaxPage(), movies);
    }
}
