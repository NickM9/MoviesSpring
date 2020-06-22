package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.dto.MovieDto;
import com.trainigcenter.springtask.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper mapper;

    @GetMapping
    public Pagination<MovieDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "2") int size) {
        return convertToPaginationDto(movieService.getAll(page, size));
    }


    @GetMapping(params = "genreId")
    public Pagination<MovieDto> getAllByGenre(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "2") int size,
                                              @RequestParam(value = "genreId") int genreId) {
        return convertToPaginationDto(movieService.getAllByGenre(page, size, genreId));
    }

    @GetMapping("/{id}")
    public MovieDto getById(@PathVariable("id") int id) {
        return mapper.toDto(movieService.getById(id).orElseThrow(() -> new NotFoundException("Movie id:" + id + " not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto save(@Valid @RequestBody MovieDto movieDto) {
        movieDto.setId(null);
        Movie movie = movieService.save(mapper.fromDto(movieDto));
        return mapper.toDto(movie);
    }

    @PutMapping("/{id}")
    public MovieDto update(@PathVariable("id") int id,
                           @Valid @RequestBody MovieDto movieDto) {
        movieDto.setId(id);
        Movie movie = movieService.save(mapper.fromDto(movieDto));
        return mapper.toDto(movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        movieService.delete(id);
    }

    private Pagination<MovieDto> convertToPaginationDto(Pagination<Movie> moviePagination) {
        List<MovieDto> moviesDto = moviePagination.getObjects()
                                                  .stream()
                                                  .map(movie -> mapper.toDto(movie))
                                                  .collect(Collectors.toList());

        Pagination<MovieDto> paginationDto = new Pagination<>();

        paginationDto.setPage(moviePagination.getPage());
        paginationDto.setMaxPage(moviePagination.getMaxPage());
        paginationDto.setSize(moviePagination.getSize());
        paginationDto.setObjects(moviesDto);

        return paginationDto;
    }
}
