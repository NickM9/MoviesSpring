package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ActorDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 45)
    private String name;

    @Min(1900)
    @Max(2010)
    private int birthYear;
}
