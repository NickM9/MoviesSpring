package com.trainigcenter.springtask.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	//@NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> genreMovies;

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + "]";
	}
    
    
    
}
