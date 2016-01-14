package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(IntegerTO historyItemId) {
        BorrowHistoryItem borrowHistoryItem = service.updateBorrowHistoryItem(historyItemId);

        if (borrowHistoryItem == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{memberUuid}")
    public Response getBorrowHistoryItemsByMember(@PathParam("memberUuid") String memberUuid,
                                                  @QueryParam("start") String start, @QueryParam("end") String end) {
        PaginationParams pagination = new PaginationParams(start, end);
        ItemsListingTO<MemberBorrowHistoryTO> borrowedHistoryItems = service.findBorrowedBooksByMember(memberUuid, pagination);

        if(borrowedHistoryItems.getItems().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<ItemsListingTO<MemberBorrowHistoryTO>>(borrowedHistoryItems){}).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{memberUuid}/size")
    public Response getBorrowHistoryItemsByMemberCount(@PathParam("memberUuid") String memberUuid) {
        Long count = service.countBorrowedBooksByMember(memberUuid);

        return Response.ok().entity(count).build();
    }

    private Response getStringResponse(Response.Status status, String message) {
        StringTO stringTO = new StringTO(message);

        return Response.status(status).entity(stringTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHistoryItems(@QueryParam("start") String start, @QueryParam("end") String end,
                                       @QueryParam("sort") String sortBy, @QueryParam("order") String order){
        PaginationParams pagination = new PaginationParams(start, end);
        SortParams sortParams = new SortParams(sortBy, order);
        ItemsListingTO<DetailedBorrowHistoryTO> borrowedBooks = service.getActiveBorrowedBooks(pagination, sortParams);

        if(borrowedBooks.getItems().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(new GenericEntity<ItemsListingTO<DetailedBorrowHistoryTO>>(borrowedBooks) {}).build();
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
        PaginationParams pagination = new PaginationParams(start, end);
        SortParams sortParams = new SortParams(sort, order);
        ItemsListingTO<DetailedBorrowHistoryTO> overdueBooks = service.getOverdueBooks(pagination, sortParams);

        if(overdueBooks.getItems().size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(new GenericEntity<ItemsListingTO<DetailedBorrowHistoryTO>>(overdueBooks) {}).build();
    }
}
