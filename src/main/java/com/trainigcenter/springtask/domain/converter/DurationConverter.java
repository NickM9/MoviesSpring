package com.trainigcenter.springtask.domain.converter;

import javax.persistence.AttributeConverter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute.get(ChronoUnit.SECONDS);
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return Duration.ofSeconds(dbData);
    }
}
