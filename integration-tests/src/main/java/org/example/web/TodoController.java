package org.example.web;

import jakarta.validation.Valid;
import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Todo> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Todo byId(@PathVariable("id") Long id) {
        return repo.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Todo create(@Valid @RequestBody Todo todo) {
        return repo.save(todo);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable("id") Long id, @Valid @RequestBody Todo incoming) {
        return repo.findById(id).map(t -> {
            t.setTitle(incoming.getTitle());
            t.setDone(incoming.isDone());
            return repo.save(t);
        }).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (!repo.existsById(id)) throw new TodoNotFoundException(id);
        repo.deleteById(id);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoNotFoundException.class)
    public void notFound() { }
}

class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Todo not found: " + id);
    }
}
