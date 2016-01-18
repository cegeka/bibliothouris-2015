package integration;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestIntegrationClient {
    private WebTarget target;

    public TestIntegrationClient(WebTarget target) {
        this.target = target;
    }

    public <T> Response post(String path, T entity) {
        return target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    public <T> Response put(String path, T entity) {
        return target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    public Response get(String path) {
        return target.path(path)
                     .request(MediaType.APPLICATION_JSON)
                     .get(Response.class);
    }

    public Response getBooksWithParams(String path, String title, String isbn, String start, String end, String sortBy, String order) {
        return target.path(path)
                .queryParam("title", title)
                .queryParam("isbn", isbn)
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("sort", sortBy)
                .queryParam("order", order)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public Response getImportedBooks(String path, String title, String isbn) {
        return target.path(path)
                .queryParam("title", title)
                .queryParam("isbn", isbn)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public Response getText(String path) {
        return target.path(path)
                .request(MediaType.TEXT_PLAIN)
                .get(Response.class);
    }

}