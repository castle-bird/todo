package com.zerock.apiserver.repository.search;

import com.zerock.apiserver.domain.Todo;
import com.zerock.apiserver.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {
    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
