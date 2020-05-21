package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.persistence.ReviewDao;
import com.trainigcenter.springtask.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review getById(Integer id) {
        return reviewDao.findById(id);
    }

    @Override
    public List<Review> getAll(int movieId, int page, int size) {
        return reviewDao.findAll(movieId, page, size);
    }

    @Override
    @Transactional
    public Review add(Review review) {

        Review dbReview = reviewDao.findByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName());

        if (dbReview == null) {
            return reviewDao.add(review);
        }

        return dbReview;
    }

    @Override
    @Transactional
    public Review update(Review review) {
        Review dbReview = reviewDao.findById(review.getId());

        if (dbReview != null) {
            return reviewDao.update(review);
        }

        return dbReview;
    }

    @Override
    public void delete(Review review) {
        reviewDao.delete(review);
    }
}
