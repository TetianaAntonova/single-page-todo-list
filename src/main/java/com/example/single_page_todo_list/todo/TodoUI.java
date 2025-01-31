package com.example.single_page_todo_list.todo;

import com.example.single_page_todo_list.todo.model.TodoRecord;
import com.example.single_page_todo_list.todo.storage.InMemoryRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;

@Route(value = "todo/:name")
public class TodoUI extends VerticalLayout implements BeforeEnterObserver {

    private final InMemoryRepository repository;
    private final InMemoryRepository inMemoryRepository;
    String author;

    public TodoUI(InMemoryRepository repository, InMemoryRepository inMemoryRepository) {
        this.repository = repository;
        this.inMemoryRepository = inMemoryRepository;
    }

    Grid<TodoRecord> grid;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        H2 title = new H2("TodoList Application: " + author);
        add(title);

        Button button = new Button("Add new ToDo");
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
        button.addClickListener(e -> {
            Dialog dialog = createAddDialogue();
            dialog.open();
        });
        add(new HorizontalLayout(button));

        grid = new Grid();
        grid.addColumn(TodoRecord::getTitle).setHeader("Title");
        grid.addColumn(TodoRecord::getDescription).setHeader("Description");
        grid.addColumn(TodoRecord::getAuthor).setHeader("Author");
        grid.addColumn(TodoRecord::getCreatedAt).setHeader("Created At");
        grid.addColumn(TodoRecord::isClosed).setHeader("Closed");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        refreshItems();
        add(grid);
    }

    private Dialog createAddDialogue() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New ToDo");

        VerticalLayout dialogueLayout = new VerticalLayout();
        TextField title = new TextField("Title");
        dialogueLayout.add(title);

        dialog.add(dialogueLayout);

        Button btnSave = new Button("Save");
        btnSave.addClickListener(e -> {
            inMemoryRepository.add(
                    TodoRecord.builder()
                            .title(title.getValue())
                            .description("Buy salad")
                            .author(author)
                            .createdAt(LocalDateTime.now())
                            .closed(true)
                            .build()
            );
            dialog.close();
            refreshItems();
        });

        Button btnCancel = new Button("Cancel");
        btnCancel.addClickListener(e -> {
            dialog.close();
        });

        dialog.getFooter().add(btnSave, btnCancel);

        return dialog;
    }

    private void refreshItems() {
        grid.setItems(repository.getRecords());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        author = event.getRouteParameters().get("name").orElse("Unknown").toUpperCase();
    }
}
