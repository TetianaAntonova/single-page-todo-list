package com.example.single_page_todo_list.todo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoRecord {
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private String author;
    private boolean closed = false;
}
