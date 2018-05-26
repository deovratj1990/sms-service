package com.sms.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {
    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    public ZonedDateTime getCurrent() {
        return ZonedDateTime.parse(getCurrentAsString(), dateTimeFormatter);
    }

    public String getCurrentAsString() {
        return ZonedDateTime.now().format(dateTimeFormatter);
    }
}
