package com.trainigcenter.springtask.domain;

import com.trainigcenter.springtask.domain.util.DurationConverter;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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

}
