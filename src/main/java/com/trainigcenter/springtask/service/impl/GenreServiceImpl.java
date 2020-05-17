package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.GenreDao;
import com.trainigcenter.springtask.dao.impl.GenreDaoImpl;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao){
        this.genreDao = genreDao;
    }

    public Genre getGenre(int genreId){
        return genreDao.findGenre(genreId);
    }

}
