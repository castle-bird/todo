package com.zerock.apiserver.repository.search;


import com.querydsl.jpa.JPQLQuery;
import com.zerock.apiserver.domain.QTodo;
import com.zerock.apiserver.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// 반드시 사용할 엔티티 클래스를 생성자에서 super() 호출 시 전달해야 한다
// 구현 클래스 이름은 반드시 커스텀 QueryDSL 인터페이스 명 + "Impl" 형식이어야 한다.
@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    // 생성자에서 QuerydslRepositorySupport에 조회할 엔티티 타입을 지정한다. (현재 사용하는 엔티티)
    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1() {

        log.info("Search1.....................................");

        // querydsl이 조회를 위해 참조하는 객체는 Q객체다 (기존 엔티티 객체를 Q객체로 변환한것)
        // .todo -> Todo엔티티를 사용했기 때문에 .todo로 접근
        QTodo todo = QTodo.todo;

        // JPQLQuery<엔티티> ->   JPQL 쿼리를 표현하는 객체 / 조회 날릴 객체
        // from(Q객체) -> 어떤 엔티티 기준으로 조회할지? 정함
        JPQLQuery<Todo> query = from(todo);

        // 위 방법 사용시, 조회시 객체 형식으로 접근 가능
        query.where(todo.title.contains("1"));

        // 페이징 작업
        Pageable pageable = PageRequest.of(1, 10, Sort.by("tno").descending());
        this.getQuerydsl().applyPagination(pageable, query);

        
        // 쿼리dsl은 fetch 전에 작업은 쿼리문을 쌓아 놓는 느낌이다
        // fetch를 해야만 쿼리문이 전체 호출이 된다
        // query.where(todo.title.contains("1"));로 타이틀에 1이 들어가는 애들을 찾고
        // Pageable pageable = PageRequest.of(1, 10, Sort.by("tno").descending());
        //this.getQuerydsl().applyPagination(pageable, query); 를 이용해 정렬하고 등등 작업을 쌓아간다
        query.fetch(); // 목록 데이터
        query.fetchCount(); // 갯수

        return null;
    }
}
