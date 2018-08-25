package com.sms.common.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DateTimeConfiguration {
    @Bean
    @Scope("singleton")
    public String dateTimeRegex() {
        return "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\+|-)[0-9]{4}$";
    }

    @Bean
    @Scope("singleton")
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    }
}
