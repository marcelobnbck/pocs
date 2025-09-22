package org.example.rest;

import org.example.model.Book;
import org.example.service.BookService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    private BookService service;

    @POST
    public Response createBook(@Valid Book book) {
        service.addBook(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    public List<Book> getBooks() {
        return service.getAllBooks();
    }

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") Long id) {
        return service.getBook(id);
    }
}
