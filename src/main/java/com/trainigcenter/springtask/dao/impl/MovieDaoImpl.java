package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.MovieDao;
import com.trainigcenter.springtask.domain.Movie;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    EntityManager entityManager;

    public Movie findMovieById(int movieId){
        return entityManager.find(Movie.class, movieId);
    }

	@Override
	public Set<Movie> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
    	Root<Movie> moviesRoot = query.from(Movie.class);
    	query.select(moviesRoot);
    	List<Movie> moviesList = entityManager.createQuery(query).getResultList();
    	Set <Movie> movies = new HashSet<>(moviesList);
        
        return movies;
	}

}
