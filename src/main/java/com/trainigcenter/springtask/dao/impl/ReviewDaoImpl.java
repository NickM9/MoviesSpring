package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.ReviewDao;
import com.trainigcenter.springtask.domain.Review;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Review findReviewById(int reviewId) {
        return entityManager.find(Review.class, reviewId);
    }

	@Override
	public Set<Review> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
    	Root<Review> reviewsRoot = query.from(Review.class);
    	query.select(reviewsRoot);
    	List<Review> reviewsList = entityManager.createQuery(query).getResultList();
    	Set <Review> reviews = new HashSet<>(reviewsList);
        
        return reviews;
	}
}
