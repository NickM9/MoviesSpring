package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.ActorDao;
import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ActorDaoImpl implements ActorDao {
	
	private static  final Logger logger = LogManager.getLogger(ActorDaoImpl.class);

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

	
    @Override
	public Actor findByActorName(String name) {
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
	public void addActor(Actor actor) {
    	entityManager.persist(actor);
		
	}
    

	@Override
	public Actor updateActor(Actor actor) {
		entityManager.detach(actor);
		return entityManager.merge(actor);
	}
	

	@Override
	public void deleteActor(Actor actor) {
		entityManager.remove(entityManager.contains(actor) ? actor : entityManager.merge(actor));
	}
    
    
}
