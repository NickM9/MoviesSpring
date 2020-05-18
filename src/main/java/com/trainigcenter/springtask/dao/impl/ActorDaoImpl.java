package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.ActorDao;
import com.trainigcenter.springtask.domain.Actor;
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
public class ActorDaoImpl implements ActorDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Actor findActorById(int actorId) {
        return entityManager.find(Actor.class, actorId);
    }

    @Override
    public Set<Actor> findAll() {
    	
    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
    	Root<Actor> actorsRoot = query.from(Actor.class);
    	query.select(actorsRoot);
    	List<Actor> actorsList = entityManager.createQuery(query).getResultList();
    	Set <Actor> actors = new HashSet<>(actorsList);
        
        return actors;
    }
    
    
}
