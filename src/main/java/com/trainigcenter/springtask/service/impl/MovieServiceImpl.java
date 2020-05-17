package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.MovieDao;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Movie getMovieById(int movieId){
        return movieDao.findMovieById(movieId);
    }
}
