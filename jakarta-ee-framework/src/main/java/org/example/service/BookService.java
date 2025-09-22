package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class BookService {

    @Inject
    private BookRepository repository;

    public void addBook(Book book) {
        repository.save(book);
    }

    public Book getBook(Long id) {
        return repository.find(id);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }
}
