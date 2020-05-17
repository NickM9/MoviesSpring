package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.GenreDao;
import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Genre findGenre(int genreId){
        return entityManager.find(Genre.class, genreId);
    }

}
