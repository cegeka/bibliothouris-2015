package integration;

import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.config.JpaConfig;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.MemberTestFixture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ITMemberResource extends JerseyTest {

    private TestIntegrationClient client;
    private static String PATH = "/member";

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        forceSet(TestProperties.CONTAINER_PORT, "0");
        resourceConfig.register(SpringLifecycleListener.class).register(RequestContextFilter.class);
        resourceConfig.registerClasses(MemberResource.class);
        resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(JpaConfig.class));
        return resourceConfig;
    }

    @Before
    public void setUpTests() {
        client = new TestIntegrationClient(target());
    }

    @Test
    public void givenAMember_POST_returnsOK() {
        Member member = MemberTestFixture.createMember();
        Response response = client.post(PATH, member);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAMember_POST_createsANewMember() {
        Member member = MemberTestFixture.createMember();
        Member actualMember = client.post(PATH, member).readEntity(Member.class);

        assertThat(actualMember.getUUID()).isNotNull();
    }

    @Test
    public void givenAMember_get_returnsTheMember() {
        Member member = MemberTestFixture.createMember();
        Member actualMember = client.post(PATH, member).readEntity(Member.class);

        Member readEntity = client.get(PATH + "/" + actualMember.getUUID()).readEntity(Member.class);
        assertThat(readEntity).isEqualTo(actualMember);
    }

    @Test
    public void givenAMember_get_returns200OK() {
        Member member = MemberTestFixture.createMember();
        Member actualMember = client.post(PATH, member).readEntity(Member.class);

        Response response = client.get(PATH + "/" + actualMember.getUUID());
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAMember_getOnNonExistentMember_returns404() {
        Response response = client.get(PATH + "/" + "randomNameHere");
        assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenAListOfMembers_GET_returnsTheListOfMembers() {
        Member member = MemberTestFixture.createMember();
        Member actualMember = client.post(PATH, member).readEntity(Member.class);
        MemberTO actualMemberTO = new MemberTO(actualMember);

        ItemsListingTO<MemberTO> readEntity = client.get(PATH).readEntity(new GenericType<ItemsListingTO<MemberTO>>(){}) ;

        assertThat(readEntity.getItems()).contains(actualMemberTO);
    }

    @Test
    public void givenAListOfMembersNames_GET_returns200OK() {
        Member member = MemberTestFixture.createMember();
        client.post(PATH, member).readEntity(Member.class);

        Response response = client.get(PATH + "/names");

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAListOfMembersNames_GET_returnsTheListOfMembersNames() {
        Member member = MemberTestFixture.createMember();
        client.post(PATH, member).readEntity(Member.class);

        List<MemberNameTO> readEntity = client.get(PATH + "/names").readEntity(new GenericType<List<MemberNameTO>>(){});

        assertThat(readEntity).contains(new MemberNameTO(member.getFirstName(), member.getLastName()));
    }
}
