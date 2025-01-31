package com.example.single_page_todo_list.todo;

import com.example.single_page_todo_list.todo.model.TodoRecord;
import com.example.single_page_todo_list.todo.storage.InMemoryRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "todo/:name")
public class TodoUI extends VerticalLayout implements BeforeEnterObserver {

    private final InMemoryRepository repository;
    String author;

    public TodoUI(InMemoryRepository repository) {
        this.repository = repository;
    }

    Grid<TodoRecord> grid;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        H2 title = new H2("TodoList Application: " + author);
        add(title);

        grid = new Grid();
        grid.addColumn(TodoRecord::getTitle).setHeader("Title");
        grid.addColumn(TodoRecord::getDescription).setHeader("Description");
        grid.addColumn(TodoRecord::getAuthor).setHeader("Author");
        grid.addColumn(TodoRecord::getCreatedAt).setHeader("Created At");
        grid.addColumn(TodoRecord::isClosed).setHeader("Closed");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        setItems();
        add(grid);
    }

    private void setItems() {
        grid.setItems(repository.getRecords());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        author = event.getRouteParameters().get("name").orElse("Unknown").toUpperCase();
    }
}
