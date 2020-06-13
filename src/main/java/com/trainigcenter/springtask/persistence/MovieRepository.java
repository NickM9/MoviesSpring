package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findMoviesByTitle(String title);
}
