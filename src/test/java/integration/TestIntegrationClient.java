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

    public Response getText(String path) {
        return target.path(path)
                .request(MediaType.TEXT_PLAIN)
                .get(Response.class);
    }

}