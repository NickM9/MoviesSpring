package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT m FROM Movie m LEFT JOIN m.genres g WHERE g.id = :genreId")
    Page<Movie> findMoviesByGenreId(@Param("genreId") Integer genreId, Pageable pageable);

    List<Movie> findByTitle(String title);
}
