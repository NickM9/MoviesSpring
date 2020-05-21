package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.persistence.ActorDao;
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

@Repository
public class ActorDaoImpl implements ActorDao {

    private static final Logger logger = LogManager.getLogger(ActorDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Actor findById(Integer id) {
        return entityManager.find(Actor.class, id);
    }

    @Override
    public List<Actor> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actorsRoot = query.from(Actor.class);
        query.select(actorsRoot);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Actor findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> root = criteriaQuery.from(Actor.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Actor> typed = entityManager.createQuery(criteriaQuery);
        Actor actor = null;

        try {
            actor = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
            actor = null;
        }

        return actor;
    }

    @Override
    public Actor findByIdWithMovies(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> root = criteriaQuery.from(Actor.class);
        root.fetch("actorMovies", JoinType.LEFT);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Actor> typed = entityManager.createQuery(criteriaQuery);
        Actor actor = null;

        try {
            actor = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
            actor = null;
        }

        return actor;
    }

    @Override
    public Actor add(Actor actor) {
        entityManager.persist(actor);
        return actor;
    }


    @Override
    public Actor update(Actor actor) {
        return entityManager.merge(actor);
    }


    @Override
    public void delete(Actor actor) {
        entityManager.remove(entityManager.contains(actor) ? actor : entityManager.merge(actor));
    }

}
