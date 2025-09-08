package com.zerock.apiserver.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        // 문자열을 LocalDate 객체로 변환하기 위함
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        // LocalDate 객체를 문자열로 변환하기 위함
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }
}
