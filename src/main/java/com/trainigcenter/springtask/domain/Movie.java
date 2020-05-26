package com.trainigcenter.springtask.domain;

import com.trainigcenter.springtask.domain.converter.DurationConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actors",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    private Set<Actor> actors;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(actors, movie.actors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, duration, genres, actors);
    }
}
