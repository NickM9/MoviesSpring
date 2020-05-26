package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;
import com.trainigcenter.springtask.persistence.ReviewDao;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Optional<Review> getById(Integer id) {
        return reviewDao.findById(id);
    }

    @Override
    public Pagination<Review> getAll(Integer movieId, int page, int size) {
        return reviewDao.findAll(movieId, page, size);
    }

    @Override
    @Transactional
    public Review create(Review review) {
        Optional<Review> dbReview = reviewDao.findByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName());

        if (dbReview.isPresent()) {
            throw new MethodNotAllowedException("Review id:" + dbReview.get().getId() + " already exists");
        }

        return reviewDao.create(review);
    }

    @Override
    @Transactional
    public Review update(Review review) {
        Optional<Review> dbReview = reviewDao.findById(review.getId());
        System.out.println(ReviewServiceImpl.class + " : " + dbReview);
        dbReview.orElseThrow(() -> new NotFoundException("Review id:" + review.getId() + " not found"));

        return reviewDao.update(review);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        reviewDao.delete(id);
    }
}
