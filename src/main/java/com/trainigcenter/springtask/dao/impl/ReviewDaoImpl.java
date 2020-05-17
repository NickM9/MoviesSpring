package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.ReviewDao;
import com.trainigcenter.springtask.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Review findReviewById(int reviewId) {
        return entityManager.find(Review.class, reviewId);
    }
}
