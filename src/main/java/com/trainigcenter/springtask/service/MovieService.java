package com.trainigcenter.springtask.service;

import java.util.Set;

import com.trainigcenter.springtask.domain.Movie;

public interface MovieService {

    public Movie getMovieById(int movieId);
    
    public Set<Movie> getAll();

}
