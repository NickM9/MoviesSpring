package com.trainigcenter.springtask.persistence.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;
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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final Logger logger = LogManager.getLogger(ReviewDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Review> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Review.class, id));
    }

    @Override
    public Optional<Review> findByMovieIdAndAuthorName(Integer movieId, String authorName) {
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
        }

        return Optional.ofNullable(review);
    }

    @Override
    public Pagination<Review> findAll(Integer movieId, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = criteriaQuery.from(Review.class);

        criteriaQuery.select(reviewRoot);
        criteriaQuery.where(criteriaBuilder.equal(reviewRoot.get("movie").get("id"), movieId));

        TypedQuery<Review> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(size);
        query.setFirstResult((page - 1) * size);

        List<Review> reviews = List.of(query.getResultList().toArray(Review[]::new));

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Review.class)));
        countQuery.where(criteriaBuilder.equal(reviewRoot.get("movie").get("id"), movieId));

        Long count = entityManager.createQuery(countQuery).getSingleResult();
        int lastPageNumber =  (int) (Math.ceil(((double) count) / ((double) size)));

        Pagination<Review> pagination = new Pagination(lastPageNumber, reviews);

        return pagination;
    }

    @Override
    public Review create(Review review) {
        entityManager.persist(review);
        return review;
    }

    @Override
    public Review update(Review review) {
        System.out.println(ReviewDaoImpl.class + " : " + review);
        return entityManager.merge(review);
    }

    @Override
    public void delete(Integer id) {
        Review review = entityManager.find(Review.class, id);

        entityManager.remove(entityManager.contains(review) ? review : entityManager.merge(review));
    }
}
