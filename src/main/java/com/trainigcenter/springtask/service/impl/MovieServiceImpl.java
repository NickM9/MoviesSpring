package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
import com.trainigcenter.springtask.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Pagination<Movie> getAll(int page, int size) {
        if (page < 0 || size < 1) {
            throw new NotFoundException("Page can't be less than 0. Size can't be less than 1");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));

        Pagination<Movie> moviePagination = convertToPagination(movieRepository.findAll(pageable));

        if (page >= moviePagination.getMaxPage()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return moviePagination;
    }

    @Override
    public Pagination<Movie> getAllByGenre(int page, int size, int genreId) {
        if (page < 0 || size < 1) {
            throw new NotFoundException("Page can't be less than 0. Size can't be less than 1");
        }

        Pageable pageable = PageRequest.of(page, size);

        Pagination<Movie> moviePagination = convertToPagination(movieRepository.findMoviesByGenreId(genreId, pageable));

        if (page >= moviePagination.getMaxPage()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return moviePagination;
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        if (movie.getId() != null && !movieRepository.existsById(movie.getId())) {
            throw new NotFoundException("Movie id: " + movie.getId() + " not found");
        }

        List<Movie> movies = movieRepository.findByTitle(movie.getTitle());

        for (Movie dbMovie : movies) {
            if (movie.equals(dbMovie)) {
                throw new BadRequestException("Movie name:" + dbMovie.getTitle() + " already exists");
            }
        }

        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie id: " + id + " not found");
        }

        movieRepository.deleteById(id);
    }

    private Pagination<Movie> convertToPagination(Page<Movie> moviePage) {
        Pagination<Movie> pagination = new Pagination<>();

        pagination.setPage(moviePage.getNumber());
        pagination.setMaxPage(moviePage.getTotalPages());
        pagination.setSize(moviePage.getSize());
        pagination.setObjects(moviePage.getContent());

        return pagination;
    }
}
