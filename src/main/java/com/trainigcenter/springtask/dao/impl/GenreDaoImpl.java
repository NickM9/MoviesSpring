package com.trainigcenter.springtask.dao.impl;

import com.trainigcenter.springtask.dao.GenreDao;
import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Genre findGenreById(int genreId){
        return entityManager.find(Genre.class, genreId);
    }
    
    @Override
    public Set<Genre> findAll(){
    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Genre> query = criteriaBuilder.createQuery(Genre.class);
    	Root<Genre> genresRoot = query.from(Genre.class);
    	query.select(genresRoot);
    	List<Genre> genresList = entityManager.createQuery(query).getResultList();
    	Set <Genre> genres = new HashSet<>(genresList);
        
        return genres;
    }

    @Override
    public Genre findByGenreName(String genreName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> root = criteriaQuery.from(Genre.class);
        criteriaQuery.select(root );
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), genreName));
        TypedQuery<Genre> typed = entityManager.createQuery(criteriaQuery);
        Genre genre = null;
        try {
        	genre = typed.getSingleResult();
        } catch (final NoResultException e) {
            genre = null;
        }
        
        return genre;
    }

	@Override
	public void addGenre(Genre genre) {
		entityManager.persist(genre);
	}

	@Override
	public Genre updateGenre(Genre genre) {
		entityManager.detach(genre);
		return entityManager.merge(genre);
	}

	@Override
	public void deleteGenre(Genre genre) {
		System.out.println(genre);
		entityManager.remove(entityManager.contains(genre) ? genre : entityManager.merge(genre));
	}
	
}
