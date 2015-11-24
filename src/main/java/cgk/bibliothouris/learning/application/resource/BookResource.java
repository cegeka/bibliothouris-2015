package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/books")
@Component
public class BookResource {

        @Autowired
        private BookService bookService;

        public void setBookService(BookService bookService) {
            this.bookService = bookService;
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Response createBook(Book book, @Context UriInfo uriInfo) {
            Book newBook = bookService.createBook(book);

            String uri = uriInfo.getAbsolutePath() + "/" + newBook.getId();
            return Response.ok().entity(newBook).location(URI.create(uri)).build();
        }
    }