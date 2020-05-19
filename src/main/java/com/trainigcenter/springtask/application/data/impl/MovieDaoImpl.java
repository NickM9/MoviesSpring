package com.trainigcenter.springtask.application.data.impl;

import com.trainigcenter.springtask.application.data.MovieDao;
import com.trainigcenter.springtask.application.domain.Genre;
import com.trainigcenter.springtask.application.domain.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    public Movie findMovieById(int movieId) {
        return entityManager.find(Movie.class, movieId);
    }

    @Override
    public Set<Movie> findMoviesByName(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("title"), title));
        TypedQuery<Movie> typed = entityManager.createQuery(criteriaQuery);
        List<Movie> moviesList = typed.getResultList();
        Set<Movie> movies = new HashSet<>(moviesList);

        return movies;
    }

    public Set<Movie> findAllFilterByGenre(String genre) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> moviesRoot = query.from(Movie.class);
        SetJoin<Movie, Genre> genres = moviesRoot.joinSet("genres");
        Predicate predicate = criteriaBuilder.equal(genres.get("name"), genre);
        query.select(moviesRoot);
        query.distinct(true).where(predicate);
        query.orderBy(criteriaBuilder.asc(moviesRoot.get("title")));
        List<Movie> moviesList = entityManager.createQuery(query).getResultList();
        Set<Movie> movies = new HashSet<>(moviesList);

        return movies;


    }

    @Override
    public List<Movie> findAll(int page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(3);
        query.setFirstResult((page - 1) * 3);
        List<Movie> movies = query.getResultList();
        return movies;
    }

    @Override
    @Transactional
    public void addMovie(Movie movie) {
        entityManager.persist(movie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Movie movie) {
        return entityManager.merge(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Movie movie) {
        entityManager.remove(entityManager.contains(movie) ? movie : entityManager.merge(movie));
    }

    @Override
    @Transactional
    public List<Movie> findMostPopularMovies(int raiting) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> moviesRoot = query.from(Movie.class);
        //SetJoin<Movie, Review> mJoin = moviesRoot.joinSet("reviews");
        moviesRoot.fetch("reviews", JoinType.LEFT);
        query.select(moviesRoot);
        query.where(criteriaBuilder.greaterThan(moviesRoot.get("raiting"), raiting));
        query.orderBy(criteriaBuilder.asc(moviesRoot.get("title")));
        List<Movie> movies = entityManager.createQuery(query).getResultList();

        return movies;
    }


}
