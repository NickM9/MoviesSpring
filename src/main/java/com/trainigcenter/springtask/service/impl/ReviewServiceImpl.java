package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.dao.ReviewDao;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public Review getReviewById(int reviewId){
        return reviewDao.findReviewById(reviewId);
    }
}
