package unit;

import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.MemberTestFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@RunWith(MockitoJUnitRunner.class)
public class MemberResourceTest {

    @InjectMocks
    private MemberResource resource;

    @Mock
    private MemberService service;

    @Mock
    private UriInfo uriInfo;

    @Test
    public void whenWePostValidMember_WeGetUUIDBack() {
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getEntity()).isEqualTo(expectedMember);
    }

    @Test
    public void whenWePostValidMember_WeGetOKStatus() {
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void whenWePostValidMember_WeGetUIDinLocation() {
        String expectedUri = "someUri";
        Member expectedMember = MemberTestFixture.createMember();
        URI expectedLocation = URI.create(expectedUri + "/" + expectedMember.getUUID());
        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);
        Mockito.when(uriInfo.getAbsolutePath()).thenReturn(URI.create(expectedUri));

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getLocation()).isEqualTo(expectedLocation);
    }

    @Test
    public void whenWePostInvalidMember_WeGetExceptionStatus() {
        Member expectedMember = MemberTestFixture.createInvalidMemberWithNoLastName();

        Mockito.when(service.createMember(expectedMember)).thenThrow(ValidationException.class);

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
