package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreDao {

    Genre findById(Integer id);

    Genre findByName(String name);

    List<Genre> findAll();

    Genre add(Genre genre);

    Genre update(Genre genre);

    void delete(Genre genre);

    Genre findByIdWithMovies(Integer id);
}
