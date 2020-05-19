package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
public class GenreDto {

    private Integer id;

    @NotBlank
    private String name;

}
