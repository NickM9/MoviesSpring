package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.persistence.GenreDao;
import com.trainigcenter.springtask.service.GenreService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> getById(Integer genreId) {
        return genreDao.findById(genreId);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    @Transactional
    public Genre create(Genre genre) {
        Optional<Genre> dbGenre = genreDao.findByName(genre.getName());

        if (dbGenre.isPresent()) {
            throw new MethodNotAllowedException("Genre name:" + genre.getName() + " already exists with id: " + dbGenre.get().getId());
        }

        return genreDao.create(genre);
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        Optional<Genre> dbGenre = genreDao.findById(genre.getId());
        dbGenre.orElseThrow(() -> new NotFoundException("Genre id:" + genre.getId() + " not found"));

        return genreDao.update(genre);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Genre genre = getByIdWithMovies(id).get();

        if (!genre.getGenreMovies().isEmpty()) {
            throw new MethodNotAllowedException("Genre id:" + id + " take part in movies");
        }

        genreDao.delete(genre.getId());
    }

    @Override
    public Optional<Genre> getByIdWithMovies(Integer id) {
        return genreDao.findByIdWithMovies(id);
    }

}