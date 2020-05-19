package com.trainigcenter.springtask.application.dao.impl;

import com.trainigcenter.springtask.application.dao.ReviewDao;
import com.trainigcenter.springtask.application.domain.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final Logger logger = LogManager.getLogger(ReviewDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Review findReviewById(int reviewId) {
        return entityManager.find(Review.class, reviewId);
    }

    @Override
    @Transactional
    public Review findByMovieIdAndAuthorName(Integer movieId, String authorName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                             criteriaBuilder.equal(root.get("movie").get("id"), movieId),
                             criteriaBuilder.equal(root.get("authorName"), authorName)
                           );


        TypedQuery<Review> typed = entityManager.createQuery(criteriaQuery);

        Review review = null;

        try {
            review = typed.getSingleResult();
        } catch (NoResultException e) {
            logger.debug(e);
            review = null;
        }

        return review;

    }

    @Override
    public Set<Review> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewsRoot = query.from(Review.class);
        query.select(reviewsRoot);
        List<Review> reviewsList = entityManager.createQuery(query).getResultList();
        Set<Review> reviews = new HashSet<>(reviewsList);

        return reviews;
    }

    @Override
    @Transactional
    public void addReview(Review review) {
        entityManager.persist(review);
    }

    @Override
    @Transactional
    public Review updateReview(Review review) {
        return entityManager.merge(review);
    }

    @Override
    @Transactional
    public void deleteReview(Review review) {
        entityManager.remove(entityManager.contains(review) ? review : entityManager.merge(review));

    }
}
