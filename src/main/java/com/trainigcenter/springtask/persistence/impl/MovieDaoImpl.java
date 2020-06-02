package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.util.Pagination;
import com.trainigcenter.springtask.persistence.MovieDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Movie> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Movie.class, id));
    }

    @Override
    public List<Movie> findMoviesByName(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("title"), title));

        TypedQuery<Movie> typed = entityManager.createQuery(criteriaQuery);

        return List.of(typed.getResultList().toArray(Movie[]::new));
    }

    @Override
    public Pagination<Movie> findAllByGenre(Integer genreId, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> moviesRoot = criteriaQuery.from(Movie.class);
        SetJoin<Movie, Genre> genres = moviesRoot.joinSet("genres");

        criteriaQuery.select(moviesRoot);
        criteriaQuery.distinct(true).where(criteriaBuilder.equal(genres.get("id"), genreId));
        criteriaQuery.orderBy(criteriaBuilder.asc(moviesRoot.get("title")));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);

        List<Movie> movies = List.of(query.getResultList().toArray(Movie[]::new));

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Movie> root = countQuery.from(Movie.class);
        SetJoin<Movie, Genre> genres1 = root.joinSet("genres");
        countQuery.select(criteriaBuilder.count(root));
        countQuery.distinct(true).where(criteriaBuilder.equal(genres.get("id"), genreId));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        int lastPageNumber =  (int) (Math.ceil(((double) count) / ((double) size)));

        return new Pagination(lastPageNumber, movies);
    }

    @Override
    public Pagination<Movie> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Movie.class)));

        Long count = entityManager.createQuery(countQuery).getSingleResult();
        int lastPageNumber =  (int) (Math.ceil(((double) count) / ((double) size)));

        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);

        List<Movie> movies = List.of(query.getResultList().toArray(Movie[]::new));

        return new Pagination(lastPageNumber, movies);
    }

    @Override
    public Movie create(Movie movie) {
        entityManager.persist(movie);
        return movie;
    }

    @Override
    public Movie update(Movie movie) {
        return entityManager.merge(movie);
    }

    @Override
    public void delete(Integer id) {
        Movie movie = entityManager.find(Movie.class, id);

        entityManager.remove(entityManager.contains(movie) ? movie : entityManager.merge(movie));
    }

}
