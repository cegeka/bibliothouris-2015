package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/borrow")
@Component
public class BorrowHistoryResource {

    @Autowired
    private BorrowHistoryService service;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrowBook(BorrowHistoryItemTO borrowHistoryItemTO) {
        try {
            BorrowHistoryItem persistedBorrowHistoryItem = service.createBorrowHistoryItem(borrowHistoryItemTO);
            String uri = uriInfo.getAbsolutePath() + "/" + persistedBorrowHistoryItem.getId();

            return Response.ok().location(URI.create(uri)).build();

        } catch (ValidationException e) {
            return getStringResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{memberUuid}")
    public Response getBorrowHistoryItemsByMember(@PathParam("memberUuid") String memberUuid,
                                                  @QueryParam("start") String start, @QueryParam("end") String end) {
        BookListingTO<MemberBorrowHistoryTO> borrowedHistoryItems = service.findBorrowedBooksByMember(memberUuid, start, end);

        if(borrowedHistoryItems.getBooks().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<BookListingTO<MemberBorrowHistoryTO>>(borrowedHistoryItems){}).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{memberUuid}/size")
    public Response getBorrowHistoryItemsByMemberCount(@PathParam("memberUuid") String memberUuid) {
        Long count = service.countBorrowedBooksByMember(memberUuid);

        return Response.ok().entity(count).build();
    }

    private Response getStringResponse(Response.Status status, String message) {
        StringTO stringTO = new StringTO();
        stringTO.setMessage(message);

        return Response.status(status).entity(stringTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHistoryItems(@QueryParam("start") String start, @QueryParam("end") String end,
                                       @QueryParam("sort") String sort, @QueryParam("order") String order){
        BookListingTO<DetailedBorrowHistoryTO> borrowedBooks = service.getActiveBorrowedBooks(start, end, sort, order);

        if(borrowedBooks.getBooks().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(new GenericEntity<BookListingTO<DetailedBorrowHistoryTO>>(borrowedBooks) {}).build();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getGlobalHistorySize(){
        Long borrowedCount = service.countBorrowedBooks();

        return Response.ok().entity(borrowedCount).build();
    }

    @GET
    @Path("/overdue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOverdueBooks(@QueryParam("start") String start, @QueryParam("end") String end,
                                    @QueryParam("sort") String sort, @QueryParam("order") String order){
        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = service.getOverdueBooks(start, end, sort, order);

        if(overdueBooks.getBooks().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(new GenericEntity<BookListingTO<DetailedBorrowHistoryTO>>(overdueBooks) {}).build();
    }
}
