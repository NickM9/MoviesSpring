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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ActorDaoImpl implements ActorDao {

    private static final Logger logger = LogManager.getLogger(ActorDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Actor> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Actor.class, id));
    }

    @Override
    public List<Actor> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actorsRoot = query.from(Actor.class);
        query.select(actorsRoot);

        return List.of(entityManager.createQuery(query).getResultList().toArray(Actor[]::new));
    }

    @Override
    public Optional<Actor> findByName(String name) {
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
        }

        return Optional.ofNullable(actor);
    }

    @Override
    public Optional<Actor> findByIdWithMovies(Integer id) {
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
        }

        return Optional.ofNullable(actor);
    }

    @Override
    public Actor create(Actor actor) {
        entityManager.persist(actor);
        return actor;
    }

    @Override
    public Actor update(Actor actor) {
        return entityManager.merge(actor);
    }

    @Override
    public void delete(Integer id) {
        Actor actor = entityManager.find(Actor.class, id);

        entityManager.remove(entityManager.contains(actor) ? actor : entityManager.merge(actor));
    }

}
