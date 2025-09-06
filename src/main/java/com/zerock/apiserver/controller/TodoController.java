package com.zerock.apiserver.controller;

import com.zerock.apiserver.domain.Todo;
import com.zerock.apiserver.dto.PageRequestDTO;
import com.zerock.apiserver.dto.PageResponseDTO;
import com.zerock.apiserver.dto.TodoDTO;
import com.zerock.apiserver.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {
        return todoService.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("List.................. {}", pageRequestDTO);

        return todoService.getList(pageRequestDTO);
    }
}
