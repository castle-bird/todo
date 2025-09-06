package com.zerock.apiserver.service;

import com.zerock.apiserver.domain.Todo;
import com.zerock.apiserver.dto.PageRequestDTO;
import com.zerock.apiserver.dto.PageResponseDTO;
import com.zerock.apiserver.dto.TodoDTO;

public interface TodoService {

    // 조회
    TodoDTO get(Long tno);

    // 작성
    Long register(TodoDTO dto);

    // 수정,삭제의 경우 실패시 따로 에러 처리를 하는것이 맞다 그러므로 void처리
    // 수정
    void modify(TodoDTO dto);

    // 삭제
    void remove(Long tno);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    // 인터페이스에서 기능 만들기 default
    // 매핑 만들어주기
    // Entity -> DTO
    default TodoDTO entityToDTO(Todo todo) {

        return TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .completed(todo.isCompleted())
                .dueDate(todo.getDueDate())
                .build();
    }


    // DTO -> Entity
    default Todo dtoToEntity(TodoDTO todoDto) {

        return Todo.builder()
                .tno(todoDto.getTno())
                .title(todoDto.getTitle())
                .content(todoDto.getContent())
                .completed(todoDto.isCompleted())
                .dueDate(todoDto.getDueDate())
                .build();
    }
}
