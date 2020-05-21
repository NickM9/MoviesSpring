package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.persistence.MovieDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    public Movie findById(Integer id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    public List<Movie> findMoviesByName(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("title"), title));

        TypedQuery<Movie> typed = entityManager.createQuery(criteriaQuery);
        return typed.getResultList();

    }

    public List<Movie> findAllByGenre(String genre, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> moviesRoot = criteriaQuery.from(Movie.class);
        SetJoin<Movie, Genre> genres = moviesRoot.joinSet("genres");

        criteriaQuery.select(moviesRoot);
        criteriaQuery.distinct(true).where(criteriaBuilder.equal(genres.get("name"), genre));
        criteriaQuery.orderBy(criteriaBuilder.asc(moviesRoot.get("title")));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);

        return query.getResultList();

    }

    @Override
    public List<Movie> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);

        return query.getResultList();
    }

    @Override
    public List<Movie> findByRating(int raiting, int page, int size) {
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

//    @Override
//    public Integer findCount() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//
//        Root<Movie> root = criteriaQuery.from(Movie.class);
//        criteriaQuery.select(criteriaBuilder.count(root));
//
//        return Math.toIntExact(entityManager.createQuery(criteriaQuery).getSingleResult());
//    }

    @Override
    public Movie add(Movie movie) {
        entityManager.persist(movie);
        return movie;
    }

    @Override
    public Movie update(Movie movie) {
        return entityManager.merge(movie);
    }

    @Override
    public void delete(Movie movie) {
        entityManager.remove(entityManager.contains(movie) ? movie : entityManager.merge(movie));
    }


}
