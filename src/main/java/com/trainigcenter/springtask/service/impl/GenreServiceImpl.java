package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.GenreRepository;
import com.trainigcenter.springtask.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getById(Integer id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        if (genre.getId() != null && !genreRepository.existsById(genre.getId())) {
            throw new NotFoundException("Genre id: " + genre.getId() + " not found");
        }

        if (genreRepository.existsByNameIgnoreCase(genre.getName())) {
            throw new BadRequestException("Genre name: " + genre.getName() + " is already exists");
        }

        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Genre genre = genreRepository.findWithMoviesById(id).orElseThrow(() -> new NotFoundException("Genre id:" + id + " not found"));

        if (!genre.getGenreMovies().isEmpty()) {
            throw new BadRequestException("Genre id: " + id + " take part in movies");
        }

        genreRepository.deleteById(genre.getId());
    }
}
