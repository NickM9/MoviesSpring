package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.GenreDao;
import com.trainigcenter.springtask.domain.Genre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GenreDaoImpl implements GenreDao {

    private static final Logger logger = LogManager.getLogger(GenreDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Genre findGenreById(Integer genreId) {
        return entityManager.find(Genre.class, genreId);
    }

    @Override
    public Genre findGenreByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> root = criteriaQuery.from(Genre.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
        TypedQuery<Genre> typed = entityManager.createQuery(criteriaQuery);
        Genre genre = null;
        try {
            genre = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
            genre = null;
        }

        return genre;
    }

    @Override
    public Genre findByGenreIdWithMovies(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> root = criteriaQuery.from(Genre.class);
        root.fetch("genreMovies", JoinType.INNER);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Genre> typed = entityManager.createQuery(criteriaQuery);
        Genre genre = null;
        try {
            genre = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
            genre = null;
        }

        return genre;

    }

    @Override
    public Set<Genre> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> query = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> genresRoot = query.from(Genre.class);
        query.select(genresRoot);
        List<Genre> genresList = entityManager.createQuery(query).getResultList();
        Set<Genre> genres = new HashSet<>(genresList);

        return genres;
    }

    @Override
    @Transactional
    public void addGenre(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    @Transactional
    public Genre updateGenre(Genre genre) {
        return entityManager.merge(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Genre genre) {
        entityManager.remove(entityManager.contains(genre) ? genre : entityManager.merge(genre));
    }

}
