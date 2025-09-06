package com.zerock.apiserver.service;

import com.zerock.apiserver.dto.PageRequestDTO;
import com.zerock.apiserver.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest // 테스트를 위한 어노테이션
@Log4j2
public class TodoServiceTests {
    @Autowired
    TodoService todoService;

    @Test
    public void testGet() {
        Long tno = 50L;

        log.info("테스트 = {}", todoService.get(tno));
    }

    @Test
    public void testRegister() {
        TodoDTO todoDto = TodoDTO.builder()
                .title("Title...")
                .content("Content...")
                .dueDate(LocalDate.now())
                .build();

        log.info(todoService.register(todoDto));
    }

    @Test
    public void testModify() {
        Long tno = 50L;
        TodoDTO todoDto = todoService.get(tno);

        todoService.modify(todoDto);
    }

    @Test
    public void testGetList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).build();

        log.info(todoService.getList(pageRequestDTO));
    }
}
