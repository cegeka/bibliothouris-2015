package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/books")
@Component
public class BookResource {

    @Autowired
    private BookService bookService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(@QueryParam("start") String start, @QueryParam("end") String end){
        List<Book> books = bookService.findAllBooks(start, end);
        if(books.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(new GenericEntity<List<Book>>(books){}).build();
    }

    @GET
    @Path("/size")
    @Produces("text/plain")
    public Response getBooksNumber(){
        Long count = bookService.countBooks();
        System.out.println(count);
        if(count == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(count).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        try {
            Book newBook = bookService.createBook(book);
            String uri = uriInfo.getAbsolutePath() + "/" + newBook.getId();
            return Response.ok().entity(newBook).location(URI.create(uri)).build();
        } catch (ValidationException e) {
            return getStringResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private Response getStringResponse(Response.Status status, String message) {
        StringTO stringTO = new StringTO();
        stringTO.setMessage(message);

        return Response.status(status).entity(stringTO).build();
    }
}