package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.persistence.ReviewDao;
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
import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final Logger logger = LogManager.getLogger(ReviewDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Review findById(Integer id) {
        return entityManager.find(Review.class, id);
    }

    @Override
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
    public List<Review> findAll(int movieId, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("movie").get("id"), movieId));

        TypedQuery<Review> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);
        return query.getResultList();

    }

    @Override
    public Review add(Review review) {
        entityManager.persist(review);
        return review;
    }

    @Override
    public Review update(Review review) {
        return entityManager.merge(review);
    }

    @Override
    public void delete(Review review) {
        entityManager.remove(entityManager.contains(review) ? review : entityManager.merge(review));

    }
}
