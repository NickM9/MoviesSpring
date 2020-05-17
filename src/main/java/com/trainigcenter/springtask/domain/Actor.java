package com.trainigcenter.springtask.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Column(name = "birth_year")
    private int birthYear;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> actorMovies = new HashSet<>();

}
