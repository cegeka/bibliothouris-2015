package cgk.bibliothouris.learning.config;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class WebAppConfig extends ResourceConfig{
    public WebAppConfig() {
        packages("cgk.bibliothouris.learning");
    }

}
