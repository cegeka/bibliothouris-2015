package cgk.bibliothouris.learning.application;

import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BookRepositoryJPA;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component()
@Path("/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(){
        List<Book> books = bookService.findAllBooks();
        if(books.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<Book>>(books){}).build();
    }

}
