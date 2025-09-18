package org.example.repository;

import org.example.model.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }

    public Book find(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}

