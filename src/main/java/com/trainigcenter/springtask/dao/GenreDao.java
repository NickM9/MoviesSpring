package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface GenreDao {

    public Genre findGenre(int genreId);

}
