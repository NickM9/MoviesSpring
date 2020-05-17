package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.MovieDao;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    EntityManager entityManager;

    public Movie findMovieById(int movieId){
        return entityManager.find(Movie.class, movieId);
    }

}
