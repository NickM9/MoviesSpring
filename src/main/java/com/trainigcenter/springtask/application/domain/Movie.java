package com.trainigcenter.springtask.application.domain;

import com.trainigcenter.springtask.application.domain.util.DurationConverter;
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
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Movie movie = (Movie) o;

		if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
		if (description != null ? !description.equals(movie.description) : movie.description != null) return false;
		if (duration != null ? !duration.equals(movie.duration) : movie.duration != null) return false;
		if (genres != null ? !genres.equals(movie.genres) : movie.genres != null) return false;
		return actors != null ? actors.equals(movie.actors) : movie.actors == null;
	}

	@Override
	public int hashCode() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (duration != null ? duration.hashCode() : 0);
		result = 31 * result + (genres != null ? genres.hashCode() : 0);
		result = 31 * result + (actors != null ? actors.hashCode() : 0);
		return result;
	}
}
