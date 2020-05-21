package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.persistence.GenreDao;
import com.trainigcenter.springtask.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getById(Integer genreId) {
        return genreDao.findById(genreId);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    @Transactional
    public Genre add(Genre genre) {
        Genre dbGenre = genreDao.findByName(genre.getName());

        if (dbGenre == null) {
            return genreDao.add(genre);
        }

        return dbGenre;
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        Genre dbGenre = genreDao.findById(genre.getId());

        if (dbGenre != null) {
            return genreDao.update(genre);
        }

        return dbGenre;
    }

    @Override
    @Transactional
    public boolean delete(Genre genre) {

        if (!genre.getGenreMovies().isEmpty()) {
            return false;
        }

        genreDao.delete(genre);
        return true;
    }

    @Override
    public Genre getByIdWithMovies(Integer id) {
        return genreDao.findByIdWithMovies(id);
    }

}
