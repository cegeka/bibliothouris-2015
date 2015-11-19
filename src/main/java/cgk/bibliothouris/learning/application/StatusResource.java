package cgk.bibliothouris.learning.application;

import cgk.bibliothouris.learning.application.transferobject.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component("statusResource")
@Path("/status")
@PropertySource("classpath:properties/${spring.profiles.active}.properties")
public class StatusResource {

    @Value("${env.name}")
    private String activeProfile;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public Response getStatus() {
        System.out.println("-- ENV -" + activeProfile +  "- LOADED -- ");
        return Response.status(Response.Status.OK).entity(new Status(activeProfile)).build();
    }

}