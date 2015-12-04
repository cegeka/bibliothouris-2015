package cgk.bibliothouris.learning.application.resource;

import cgk.bibliothouris.learning.application.transferobject.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

@Component("statusResource")
@Path("/status")
@PropertySource("classpath:properties/${spring.profiles.active:dev}.properties")
public class StatusResource {

    @Value("${env.name}")
    private String activeProfile;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public Response getStatus() {
        Status status = Status.StatusBuilder.status().withEnvironment(activeProfile).withUpTime(getServerUpTime()).build();

        return Response.status(Response.Status.OK).entity(status).build();
    }

    private String getServerUpTime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long upTime = rb.getUptime();

        long days = TimeUnit.MILLISECONDS.toDays(upTime);
        long hours = TimeUnit.MILLISECONDS.toHours(upTime) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(upTime) - TimeUnit.DAYS.toMinutes(days) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(upTime) - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);

        return String.format("%d days, %d hours, %d minutes and %d seconds", days, hours, minutes, seconds);
    }
}