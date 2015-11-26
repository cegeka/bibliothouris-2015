package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.Status;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class SecurityEndpoint {

    @POST
    public Response logout() {
        return Response.ok().entity(new Status("logged out successfully")).build();
    }

    @POST
    public Response login() {
        return Response.ok().entity(new Status("logged in successfully")).build();
    }

}
