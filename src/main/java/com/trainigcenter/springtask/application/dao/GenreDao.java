package com.trainigcenter.springtask.application.dao;

import com.trainigcenter.springtask.application.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface GenreDao {

    Genre findGenreById(Integer genreId);

    Genre findGenreByName(String name);

    Set<Genre> findAll();

    void addGenre(Genre genre);

    Genre updateGenre(Genre genre);

    void deleteGenre(Genre genre);

    Genre findByGenreIdWithMovies(Integer id);
}
