package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.persistence.GenreDao;
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
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private static final Logger logger = LogManager.getLogger(GenreDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Genre findById(Integer id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre findByName(String name) {
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
    public Genre findByIdWithMovies(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);

        Root<Genre> root = criteriaQuery.from(Genre.class);
        root.fetch("genreMovies", JoinType.LEFT);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Genre> typed = entityManager.createQuery(criteriaQuery);
        Genre genre = null;

        try {
            genre = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
        }

        return genre;
    }

    @Override
    public List<Genre> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> query = criteriaBuilder.createQuery(Genre.class);

        Root<Genre> genresRoot = query.from(Genre.class);
        query.select(genresRoot);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Genre add(Genre genre) {
        entityManager.persist(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        return entityManager.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        entityManager.remove(entityManager.contains(genre) ? genre : entityManager.merge(genre));
    }

}
