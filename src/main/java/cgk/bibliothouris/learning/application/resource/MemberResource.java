package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/member")
@Component
public class MemberResource {

    @Autowired
    private MemberService service;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMember(Member member) {
        try {
            Member createdMember = service.createMember(member);
            String uri = uriInfo.getAbsolutePath() + "/" + createdMember.getUUID();

            return Response.ok().entity(createdMember).location(URI.create(uri)).build();

        } catch (ValidationException e) {
            return getStringResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private Response getStringResponse(Response.Status status, String message) {
        StringTO stringTO = new StringTO(message);

        return Response.status(status).entity(stringTO).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{memberId}")
    public Response getMember(@PathParam("memberId") String memberId) {
        Member foundMember = service.findMember(memberId);

        if (foundMember == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(foundMember).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMembers(@QueryParam("start") String start, @QueryParam("end") String end,
                                  @QueryParam("sort") String sort, @QueryParam("order") String order) {
        ItemsListingTO<MemberTO> foundMembers = service.findAllMembers(start, end, sort, order);

        if (foundMembers.getItems().isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(new GenericEntity<ItemsListingTO<MemberTO>>(foundMembers) {}).build();
    }
}
