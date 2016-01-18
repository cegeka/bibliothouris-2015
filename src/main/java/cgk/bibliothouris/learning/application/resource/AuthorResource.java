package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.service.AuthorService;
import cgk.bibliothouris.learning.service.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/authors")
@Component
public class AuthorResource {

    @Autowired
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors(){
        List<Author> authors = authorService.findAllAuthors();

        if(authors.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<Author>>(authors){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/firstnames")
    public Response getAllAuthorsFirstNames(){
        List<StringTO> authors = authorService.findAllBookAuthorFirstName();

        if(authors.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<StringTO>>(authors){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/lastnames")
    public Response getAllAuthorsLastNames(){
        List<StringTO> authors = authorService.findAllBookAuthorLastName();

        if(authors.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<StringTO>>(authors){}).build();
    }
}
