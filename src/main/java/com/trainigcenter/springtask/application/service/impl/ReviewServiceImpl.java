package com.trainigcenter.springtask.application.service.impl;

import com.trainigcenter.springtask.application.dao.ReviewDao;
import com.trainigcenter.springtask.application.domain.Review;
import com.trainigcenter.springtask.application.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public Review getReviewById(int reviewId) {
        return reviewDao.findReviewById(reviewId);
    }

    @Override
    public Set<Review> getAll() {
        return reviewDao.findAll();
    }

    @Override
    public void addReview(Review review) {

        Review dbReview = reviewDao.findByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName());

        if (dbReview == null) {
            reviewDao.addReview(review);
        }
    }

    @Override
    public Review updateReview(Review review) {
        Review dbReview = reviewDao.findReviewById(review.getId());

        if (dbReview != null) {
            return reviewDao.updateReview(review);
        }

        return dbReview;
    }

    @Override
    public void deleteReview(Review review) {
        reviewDao.deleteReview(review);
    }
}
