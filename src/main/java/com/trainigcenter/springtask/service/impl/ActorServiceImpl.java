package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.ActorDao;
import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {

    ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    public Actor getActorById(int actorId){
        return actorDao.findActorById(actorId);
    }
}
