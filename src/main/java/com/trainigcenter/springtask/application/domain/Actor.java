package com.trainigcenter.springtask.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;


@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(name = "birth_year")
    private int birthYear;

    @ManyToMany(mappedBy = "actors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Movie> actorMovies;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Actor actor = (Actor) o;

		if (birthYear != actor.birthYear) return false;
		if (id != null ? !id.equals(actor.id) : actor.id != null) return false;
		return name != null ? name.equals(actor.name) : actor.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + birthYear;
		return result;
	}
}
