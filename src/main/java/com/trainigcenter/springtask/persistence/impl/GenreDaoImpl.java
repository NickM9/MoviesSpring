package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Actor;
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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoImpl implements GenreDao {

    private static final Logger logger = LogManager.getLogger(GenreDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
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
        }

        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<Genre> findByIdWithMovies(Integer id) {
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

        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> query = criteriaBuilder.createQuery(Genre.class);

        Root<Genre> genresRoot = query.from(Genre.class);
        query.select(genresRoot);

        return List.of(entityManager.createQuery(query).getResultList().toArray(Genre[]::new));
    }

    @Override
    public Genre create(Genre genre) {
        entityManager.persist(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        return entityManager.merge(genre);
    }

    @Override
    public void delete(Integer id) {
        Genre genre = entityManager.find(Genre.class, id);

        entityManager.remove(entityManager.contains(genre) ? genre : entityManager.merge(genre));
    }

}
