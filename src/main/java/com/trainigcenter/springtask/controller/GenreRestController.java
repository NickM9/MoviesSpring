package com.trainigcenter.springtask.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.dto.Error;
import com.trainigcenter.springtask.dto.GenreDto;
import com.trainigcenter.springtask.exception.NotFoundException;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.service.impl.GenreServiceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreRestController {
	
	private static  final Logger logger = LogManager.getLogger(GenreRestController.class);
	
	private GenreService genreService;
	private ModelMapper modelMapper;
	
	@Autowired
	public GenreRestController(GenreService genreService, ModelMapper modelMapper) {
		this.genreService = genreService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
    public Set<GenreDto> getAll() {
		Set<Genre> allGenres = genreService.getAll();
		Set<GenreDto> allGenreDto = allGenres.stream().map(this::converToDto).collect(Collectors.toSet());
        return allGenreDto;
    }
	
//	@GetMapping("/{genreId:\\d+}")
//	public GenreDto getGenreById(@PathVariable int genreId) {
//		Genre genre = genreService.getGenreById(genreId);
//		GenreDto genreDto = convertoDto(genre);
//		return genreDto;
//	}
	
	@GetMapping("/{genreName}")
	public GenreDto getGenreByName(@PathVariable String genreName) {
		genreName = mapGenreName(genreName);

		Genre genre = genreService.getGenreByName(genreName);
		
//		HttpStatus status = null;
//		GenreDto genreDto = null;
//		
//		if (genre == null) {
//			status = HttpStatus.NOT_FOUND;
//		} else {
//			status = HttpStatus.OK;
//			genreDto = convertoDto(genre);
//		}
		
		if (genre == null) {
			throw new NotFoundException(genreName);
		}
		
		return converToDto(genre);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveGenre(@Valid @RequestBody GenreDto genreDto) {
		String genreName = genreDto.getName();
		
		genreName = mapGenreName(genreName);
		genreDto.setName(genreName);
		
		Genre genre = convertFromDto(genreDto);
		genreService.addGenre(genre);
	}
	
	@PutMapping("/{genreName}")
	@ResponseStatus(HttpStatus.OK)
	public GenreDto updateGenre (@PathVariable String genreName, @RequestBody GenreDto genreDto) {
		
		genreName = mapGenreName(genreName);
		
		String genreDtoName = genreDto.getName();
		genreDtoName = mapGenreName(genreDtoName);
		genreDto.setName(genreDtoName);
		
		Genre genreDb = genreService.getGenreByName(genreName);
		
		if (genreDb == null) {
			throw new NotFoundException(genreName);
		}
		
		genreDb.setName(genreDto.getName());
		Genre genre = genreService.updateGenre(genreDb);
		
		return converToDto(genre);
	}
	
	@DeleteMapping("/{genreName}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteGenre(@PathVariable String genreName) {
		Genre genre = genreService.getGenreByName(genreName);
		genreService.deleteGenre(genre);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	private Error genreNotFound(NotFoundException e){
		Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName() + " not found");
		return error;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Error handleBadInput(MethodArgumentNotValidException e) {
		Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Bad request. You should init the genre name");
		return error;
	}
	
	private GenreDto converToDto(Genre genre) {
		return modelMapper.map(genre, GenreDto.class);
	}
	
	private Genre convertFromDto(GenreDto genreDto) {
		return modelMapper.map(genreDto, Genre.class);
	}
	
	private String mapGenreName(String genreName) {
		genreName = genreName.toLowerCase();
		genreName.replaceAll("-", " ");
		return genreName;
	}
	
}
