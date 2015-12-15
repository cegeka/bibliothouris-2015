package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/borrowedBooks")
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
        List<MemberBorrowHistoryTO> borrowedHistoryItems = service.findBorrowedBooksByMember(memberUuid, start, end);

        if(borrowedHistoryItems.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<List<MemberBorrowHistoryTO>>(borrowedHistoryItems){}).build();
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
}
