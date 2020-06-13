package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.dto.MovieDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<MovieDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "2") int size) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        return convertToPaginationDto(movieService.getAll(page, size));
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
        Movie movie = movieService.getById(id).orElseThrow(() -> new NotFoundException("Movie id: " + id + " not found"));
        movieService.delete(movie.getId());
    }

    private MovieDto convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    private Movie convertFromDto(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    private Page<MovieDto> convertToPaginationDto(Page<Movie> moviePagination) {
        return moviePagination.map(this::convertToDto);
    }
}
