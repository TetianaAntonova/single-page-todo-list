package com.example.single_page_todo_list.todo.storage;

import com.example.single_page_todo_list.todo.model.TodoRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryRepository {

    private final List<TodoRecord> records = new ArrayList<>();

    @PostConstruct
    void setup() {
        for (int i = 1; i <= 10; i++) {
            add(TodoRecord.builder()
                    .title("# " + i)
                    .description("Buy salad")
                    .author("John Doe")
                    .createdAt(LocalDateTime.now())
                    .closed(true)
                    .build());
        }
    }

    public void add(TodoRecord record) {
        records.add(record);
    }

    public List<TodoRecord> getRecords() {
        return records;
    }
}
