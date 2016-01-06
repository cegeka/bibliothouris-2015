package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
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
    public Response getAllBooks(@QueryParam("start") String start, @QueryParam("end") String end,
                                @QueryParam("title") String title, @QueryParam("isbn") String isbn){
        ItemsListingTO bookListingTO = bookService.findAllBooks(start, end, title, isbn);
        if(bookListingTO.getItems().size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(new GenericEntity<ItemsListingTO<BookWithStatusTO>>(bookListingTO){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/import")
    public Response getAllImportedBooks(@QueryParam("title") String title, @QueryParam("isbn") String isbn){
        try {
            List<Book> listOfImportedBooks = bookService.importContent(title, isbn);
            if(listOfImportedBooks.size() == 0){
                return Response.status(Status.NOT_FOUND).build();
            }
            return Response.ok().entity(new GenericEntity<List<Book>>(listOfImportedBooks){}).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Status.NOT_FOUND).build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableBooks(@QueryParam("start") String start, @QueryParam("end") String end,
                                @QueryParam("title") String title, @QueryParam("isbn") String isbn){
        ItemsListingTO bookListingTO = bookService.findAllAvailableBooks(start, end, title, isbn);
        if(bookListingTO.getItems().size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(new GenericEntity<ItemsListingTO<BookTO>>(bookListingTO){}).build();
    }

    @GET
    @Path("/size")
    @Produces("text/plain")
    public Response getBooksNumber(){
        Long count = bookService.countBooks();
        System.out.println(count);
        if(count == 0){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(count).build();
    }

    @GET
    @Path("/available/size")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAvailableBooksNumber(){
        Long count = bookService.countAvailableBooks();
        if(count == 0){
            return Response.status(Status.NOT_FOUND).build();
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
            return getStringResponse(Status.BAD_REQUEST, e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{bookId}")
    public Response getBook(@PathParam("bookId") Integer bookId) {
        Book book = bookService.findBookById(bookId);

        if(book == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok().entity(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/titles")
    public Response getBookTitles() {
        List<StringTO> bookTitles = bookService.findAllBookTitles();

        if(bookTitles.size() == 0)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<List<StringTO>>(bookTitles){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/isbnCodes")
    public Response getBookIsbnCodes() {
        List<StringTO> bookIsbnCodes = bookService.findAllBookIsbnCodes();

        if(bookIsbnCodes.size() == 0)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<List<StringTO>>(bookIsbnCodes){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{bookId}/borrowedBy")
    public Response getBorrowerDetails(@PathParam("bookId") Integer bookId) {
        BookBorrowerTO bookBorrowerTO = bookService.findBookBorrowerDetails(bookId);

        return Response.ok().entity(bookBorrowerTO).build();
    }

    private Response getStringResponse(Status status, String message) {
        StringTO stringTO = new StringTO(message);

        return Response.status(status).entity(stringTO).build();
    }
}