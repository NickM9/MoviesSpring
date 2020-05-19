package com.trainigcenter.springtask.application.service.impl;

import com.trainigcenter.springtask.application.dao.GenreDao;
import com.trainigcenter.springtask.application.domain.Genre;
import com.trainigcenter.springtask.application.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre getGenreById(int genreId) {
        return genreDao.findGenreById(genreId);
    }

    @Override
    public Set<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    public void addGenre(Genre genre) {
        Genre dbGenre = genreDao.findGenreByName(genre.getName());

        if (dbGenre == null) {
            genreDao.addGenre(genre);
        }
    }

    @Override
    public Genre updateGenre(Genre genre) {
        Genre dbGenre = genreDao.findGenreById(genre.getId());

        if (dbGenre != null) {
            return genreDao.updateGenre(genre);
        }

        return dbGenre;
    }

    @Override
    public boolean deleteGenre(Genre genre) {

        if (!genre.getGenreMovies().isEmpty()) {
            return false;
        }

        genreDao.deleteGenre(genre);
        return true;
    }

    @Override
    public Genre getGenreByIdWithMovies(Integer id) {
        return genreDao.findByGenreIdWithMovies(id);
    }

}
