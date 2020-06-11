package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.persistence.GenreRepository;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getById(Integer genreId) {
        return genreRepository.findById(genreId);
    }

    @Override
    @Transactional
    public Genre create(Genre genre) {
        genre.setId(null);

        Optional<Genre> dbGenre = genreRepository.findByNameIgnoreCase(genre.getName());

        if (dbGenre.isPresent()) {
            throw new MethodNotAllowedException("Genre name: " + dbGenre.get().getName() + " already exists with id: " + dbGenre.get().getId());
        }

        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public Genre update(Genre genre, int id) {
        genre.setId(id);

        Optional<Genre> dbGenre = genreRepository.findById(genre.getId());
        dbGenre.orElseThrow(() -> new NotFoundException("Genre id: " + genre.getId() + " not found"));

        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> getByIdWithMovies(Integer id) {
        return genreRepository.findWithMoviesById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Genre genre = getByIdWithMovies(id).get();

        if (!genre.getGenreMovies().isEmpty()) {
            throw new MethodNotAllowedException("Genre id: " + id + " take part in movies");
        }

        genreRepository.deleteById(genre.getId());
    }
}
