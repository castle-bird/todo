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
        // 클라이언트의 요청시 작동
        // ex) /api?a=2020-12-12&b=today 에서 2020-12-12을 LocalDate타입으로 변경하여 자료 비교에 용이  
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        // LocalDate 객체를 문자열로 변환하기 위함
        // 서버가 클라이언트에게 답변을 줄때 작동
        // java단에서 작업한 날짜를 보통 응답할 때 json 형태로 많이 주기때문에 text로 변경하여 전달 
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }

    /*
    * parse()와 print()에 대한 ai 답변 :
    *
    * parse() 메서드는 클라이언트가 서버로 요청(Request) 을 보낼 때,
    * 요청의 파라미터(예: GET 쿼리 파라미터, POST 폼 데이터 등)에 포함된 문자열을 Java 객체(LocalDate)로 변환하는 데 사용됩니다.
    *
    * print() 메서드는 서버가 클라이언트로 응답(Response) 을 보낼 때,
    * Java 객체(LocalDate)를 문자열로 변환하는 데 사용됩니다. 응답을 JSON, XML, 뷰 템플릿 등으로 렌더링할 때 날짜 객체를 문자열로 포맷하는 과정에서 활용됩니다.
    *
    * 요약하면 parse()는 요청 바인딩(문자열 → 객체) 시,
    * print()는 응답 직렬화(객체 → 문자열) 시 작동
    * */
}
