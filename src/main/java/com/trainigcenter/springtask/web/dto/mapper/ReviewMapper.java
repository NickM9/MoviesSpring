package com.trainigcenter.springtask.web.dto.mapper;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toDto(Review review);

    Review fromDto(ReviewDto reviewDto);

}
