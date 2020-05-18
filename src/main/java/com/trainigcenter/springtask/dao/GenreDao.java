package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.Set;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GenreDao {

    public Genre findGenreById(int genreId);
    public Set<Genre> findAll();
    public Genre findByGenreName(String name);
    public void addGenre(Genre genre);
    public Genre updateGenre(Genre genre);
    public void deleteGenre(Genre genre);
}
