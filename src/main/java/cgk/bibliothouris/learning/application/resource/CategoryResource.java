package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.service.CategoryService;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Component
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCategories(){
        List<BookCategory> categories = categoryService.findAllCategories();

        if(categories.size() == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(new GenericEntity<List<BookCategory>>(categories){}).build();
    }

}
