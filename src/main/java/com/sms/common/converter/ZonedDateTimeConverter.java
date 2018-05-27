package com.sms.common.converter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Converter
@Component
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String>, ApplicationContextAware {
    private static DateTimeFormatter dateTimeFormatter;

    @Override
    public String convertToDatabaseColumn(ZonedDateTime attribute) {
        if(attribute != null) {
            return attribute.format(ZonedDateTimeConverter.dateTimeFormatter);
        } else {
            return null;
        }
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        if(dbData != null) {
            return ZonedDateTime.parse(dbData, ZonedDateTimeConverter.dateTimeFormatter);
        } else {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ZonedDateTimeConverter.dateTimeFormatter = applicationContext.getBean(DateTimeFormatter.class);
    }
}
