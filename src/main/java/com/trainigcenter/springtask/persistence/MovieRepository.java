package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Page<Movie> findAll(Pageable pageable);

    Optional<Movie> findById(Integer id);

    List<Movie> findMoviesByTitle(String title);

    Movie save(Movie movie);

    void deleteById(Integer id);
}
