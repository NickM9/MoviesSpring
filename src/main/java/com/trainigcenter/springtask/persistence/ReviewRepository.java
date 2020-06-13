package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findAllByMovieId(Integer movieId, Pageable pageable);

    Optional<Review> findByMovieIdAndAuthorName(Integer movieId, String authorName);

}
