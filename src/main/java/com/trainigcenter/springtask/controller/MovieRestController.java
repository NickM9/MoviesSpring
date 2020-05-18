package com.trainigcenter.springtask.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.dto.MovieDto;
import com.trainigcenter.springtask.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieRestController {
	
	private MovieService movieService;
	private ModelMapper modelMapper;
	
	@Autowired
	public MovieRestController(MovieService movieService, ModelMapper modelMapper) {
		this.movieService = movieService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
    public Set<MovieDto> getAll() {
		Set<Movie> allMovies = movieService.getAll();
		Set<MovieDto> allMovieDto = allMovies.stream().map(this::convertoDto).collect(Collectors.toSet());
        return allMovieDto;
    }
	
	@GetMapping("/{movieId}")
	public MovieDto getMovieById(@PathVariable int movieId) {
		Movie movie = movieService.getMovieById(movieId);
		MovieDto movieDto = convertoDto(movie);
		return movieDto;
	}
	
	private MovieDto convertoDto(Movie movie) {
		return modelMapper.map(movie, MovieDto.class);
	}
}
