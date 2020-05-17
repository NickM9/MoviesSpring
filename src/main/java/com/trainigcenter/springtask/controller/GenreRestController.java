package com.trainigcenter.springtask.controller;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreRestController {
	
	private GenreService genreService;
	
	@Autowired
	public GenreRestController(GenreService genreService) {
		this.genreService = genreService;
	}
	
	@GetMapping
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }
	
	@GetMapping("/{genreId}")
	public Genre getGenre(@PathVariable int genreId) {
		Genre genre = genreService.getGenre(genreId);
		System.out.println(genre);
		return genre;
	}
	
}
