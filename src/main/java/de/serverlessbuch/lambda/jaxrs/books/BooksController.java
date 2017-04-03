package de.serverlessbuch.lambda.jaxrs.books;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BooksController {

    @Inject
    private BookService service;

    @GET
    public List<Book> getBooks() {
        List<Book> books = service.getBooks();
        return books;
    }

    @POST
    public List<Book> addBook(Book book) {
        List<Book> books = service.addBook(book);
        return books;
    }

}
