package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.GenreDao;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
	
	private static  final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao){
        this.genreDao = genreDao;
    }

    public Genre getGenreById(int genreId){
        return genreDao.findGenreById(genreId);
    }

	@Override
	public Set<Genre> getAll() {
		return genreDao.findAll();
	}

	@Override
	public Genre getGenreByName(String name) {
		return genreDao.findByGenreName(name);
	}

	@Override
	public void addGenre(Genre genre) {
		Genre dbGenre = genreDao.findByGenreName(genre.getName());;
		
		if (dbGenre == null) {
			genreDao.addGenre(genre);
		}
	}

	@Override
	public Genre updateGenre(Genre genre) {
		Genre dbGenre = genreDao.findByGenreName(genre.getName());
		
		if (genre != null) {
			return genreDao.updateGenre(genre);
		}
		
		return dbGenre;
	}

	@Override
	public void deleteGenre(Genre genre) {
		genreDao.deleteGenre(genre);
	}

}
