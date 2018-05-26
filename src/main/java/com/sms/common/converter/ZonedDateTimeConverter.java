package com.sms.common.converter;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Converter
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {
    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @Override
    public String convertToDatabaseColumn(ZonedDateTime attribute) {
        return attribute.format(dateTimeFormatter);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        return ZonedDateTime.parse(dbData, dateTimeFormatter);
    }
}
