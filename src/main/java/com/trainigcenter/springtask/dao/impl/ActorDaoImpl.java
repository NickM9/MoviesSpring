package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.ActorDao;
import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ActorDaoImpl implements ActorDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Actor findActorById(int actorId) {
        return entityManager.find(Actor.class, actorId);
    }
}
