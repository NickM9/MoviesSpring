package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.persistence.GenreRepository;
import com.trainigcenter.springtask.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
