package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ActorDto {

    private Integer id;

    @NotBlank
    private String name;

    @Min(1900)
    private int birthYear;
}
