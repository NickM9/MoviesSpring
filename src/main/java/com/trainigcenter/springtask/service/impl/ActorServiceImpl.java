package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.ActorDao;
import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.service.ActorService;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {
	
	private static  final Logger logger = LogManager.getLogger(ActorServiceImpl.class);

    ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    public Actor getActorById(int actorId){
        return actorDao.findActorById(actorId);
    }

	@Override
	public Set<Actor> getAll() {
		return actorDao.findAll();
	}

	@Override
	public Actor getActorByName(String name) {
		return actorDao.findByActorName(name);
	}

	@Override
	public void addActor(Actor actor) {
		Actor dbActor = actorDao.findByActorName(actor.getName());
		
		if (dbActor == null) {
			actorDao.addActor(actor);
		}
		
	}

	@Override
	public Actor updateActor(Actor actor) {
		Actor dbActor = actorDao.findByActorName(actor.getName());
		
		if (actor != null) {
			return actorDao.updateActor(actor);
		}
		
		return dbActor;
	}

	@Override
	public void deleteActor(Actor actor) {
		actorDao.deleteActor(actor);
	}
    
    
}
