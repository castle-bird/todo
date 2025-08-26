package com.zerock.apiserver.repository;

import com.zerock.apiserver.domain.Todo;
import com.zerock.apiserver.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

// TodoSearch = querydsl을 위함
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
