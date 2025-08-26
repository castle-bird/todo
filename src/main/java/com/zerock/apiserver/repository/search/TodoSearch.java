package com.zerock.apiserver.repository.search;

import com.zerock.apiserver.domain.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {
    Page<Todo> search1();
}
