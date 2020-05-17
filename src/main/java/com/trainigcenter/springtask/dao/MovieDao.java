package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Movie;

public interface MovieDao {

    public Movie findMovieById(int movieId);

}
