package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Service;

@Service
public interface GenreService {

    public Genre getGenre(int genreId);

}
