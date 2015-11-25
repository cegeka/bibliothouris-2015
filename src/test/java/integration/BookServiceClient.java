package integration;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookServiceClient {
    private WebTarget target;

    public BookServiceClient(WebTarget target) {
        this.target = target;
    }

    public <T> Response post(String path, T entity) {
        return target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }
}