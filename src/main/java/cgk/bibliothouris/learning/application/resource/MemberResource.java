package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/member")
@Component
public class MemberResource {

    @Autowired
    MemberService service;


    @POST
    public Response addMember(Member member) {
        Member createdMember = service.createMember(member);

        return Response.ok().entity(createdMember).build();
    }
}
