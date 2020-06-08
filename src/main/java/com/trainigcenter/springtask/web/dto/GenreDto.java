package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class GenreDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 45)
    private String name;

    @Override
    public String toString() {
        return "GenreDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
