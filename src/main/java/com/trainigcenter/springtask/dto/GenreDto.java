package com.trainigcenter.springtask.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GenreDto {

	@NotBlank
    private String name;
    
}
