package com.zerock.apiserver.repository;

import com.zerock.apiserver.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest // 테스트를 위한 어노테이션
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;


    @Test
    public void test1() {
        // TodoRepository 있는지?
        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void testInsert() {
        // 추가

        Todo todo = Todo.builder()
                .title("Title")
                .content("Content...")
                .dueDate(LocalDate.of(2023, 12, 30))
                .build();

        Todo result = todoRepository.save(todo);

        log.info("RESULT = {}", result);
    }

    @Test
    public void testRead() {
        // 조회

        Long tno = 1L;

        // findById의 리턴값은 Optional이다.
        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info("Todo = {}", todo);
    }

    @Test
    public void testUpdate() {
        // 수정

        Long tno = 1L;

        // findById의 리턴값은 Optional이다.
        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        todo.changeTitle("Updated Title");
        todo.changeContent("Updated Content...");
        todo.changeCompleted(true);

        todoRepository.save(todo);
    }

    @Test
    public void testPaging() {
        // 페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
    }
}
