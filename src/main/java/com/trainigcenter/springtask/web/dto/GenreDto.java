package com.trainigcenter.springtask.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 45)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String name;

}
