package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieServiceImpl movieService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private List<Review> reviews;
    private Pageable pageable;
    private Page page;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        review = new Review();
        review.setId(1);
        review.setAuthorName("Joe Russo");
        review.setTitle("Tittle, very good film!");
        review.setText("The big text about The avengers film");
        review.setRating(8.7);

        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("The Avengers");
        movie.setDescription("Avengers description. Very good!");
        movie.setDuration(Duration.ofSeconds(8520));

        Genre action = new Genre();
        action.setId(1);
        action.setName("Action");

        Genre comedy = new Genre();
        comedy.setId(2);
        comedy.setName("Comedy");

        Genre scienceFiction = new Genre();
        scienceFiction.setId(3);
        scienceFiction.setName("Science fiction");

        movie.setGenres(Set.of(action, comedy, scienceFiction));

        Actor robertDowney = new Actor();
        robertDowney.setId(1);
        robertDowney.setName("Robert Downey Jr.");
        robertDowney.setBirthYear(1965);

        Actor chrisEvans = new Actor();
        chrisEvans.setId(2);
        chrisEvans.setName("Chris Evans");
        chrisEvans.setBirthYear(1981);

        Actor chrisHemsworth = new Actor();
        chrisHemsworth.setId(3);
        chrisHemsworth.setName("Chris Hemsworth");
        chrisHemsworth.setBirthYear(1983);

        movie.setActors(Set.of(robertDowney, chrisEvans, chrisHemsworth));

        review.setMovie(movie);
        pageable = PageRequest.of(0, 2);
        reviews = List.of(review);
        page = new PageImpl(reviews);
    }

    @Test
    public void getAllTest() {
        when(reviewRepository.findAllByMovieId(review.getMovie().getId(), pageable)).thenReturn(page);
        List<Review> reviewsActual = reviewService.getAll(review.getMovie().getId(), pageable).getContent();

        assertEquals(reviewsActual, reviews);
    }

    @Test
    public void getByIdTest() {
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Review r = reviewService.getById(review.getId()).get();

        assertNotNull(r);
    }

    @Test
    public void createTest() {
        Review r = new Review();
        r.setId(999);
        r.setAuthorName("Author name");
        r.setTitle("Tittle");
        r.setText("Text");
        r.setRating(6.6);
        r.setMovie(review.getMovie());

        when(this.movieService.getById(r.getMovie().getId())).thenReturn(Optional.of(r.getMovie()));

        when(reviewRepository.save(r)).thenReturn(r);
        assertThat(reviewService.create(r, r.getMovie().getId()), is(notNullValue()));
    }

    @Test
    public void deleteTest() {
        Review r = new Review();
        r.setId(999);
        r.setAuthorName("Author name");
        r.setTitle("Tittle");
        r.setText("Text");
        r.setRating(6.6);
        r.setMovie(review.getMovie());

        reviewService.delete(r.getId());
        verify(reviewRepository, times(1)).deleteById(r.getId());
    }
}
