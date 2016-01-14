package unit;

import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberTO;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.MemberTestFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

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

        assertThat(response.getEntity()).isEqualTo(expectedMember);
    }

    @Test
    public void whenWePostValidMember_WeGetOKStatus() {
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);

        Response response = resource.addMember(expectedMember);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void whenWePostValidMember_WeGetUIDinLocation() {
        String expectedUri = "someUri";
        Member expectedMember = MemberTestFixture.createMember();
        URI expectedLocation = URI.create(expectedUri + "/" + expectedMember.getUUID());
        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);
        Mockito.when(uriInfo.getAbsolutePath()).thenReturn(URI.create(expectedUri));

        Response response = resource.addMember(expectedMember);

        assertThat(response.getLocation()).isEqualTo(expectedLocation);
    }

    @Test
    public void whenWePostInvalidMember_WeGetExceptionStatus() {
        Member expectedMember = MemberTestFixture.createInvalidMemberWithNoLastName();

        Mockito.when(service.createMember(expectedMember)).thenThrow(ValidationException.class);

        Response response = resource.addMember(expectedMember);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void whenWeGetOnAValidMember_WeGetThatMember(){
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.findMember(expectedMember.getUUID())).thenReturn(expectedMember);

        Response actualMember = resource.getMember(expectedMember.getUUID());

        assertThat(actualMember.getEntity()).isEqualTo(expectedMember);
    }

    @Test
     public void whenWeGetOnAValidMember_WeGet200OK(){
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.findMember(expectedMember.getUUID())).thenReturn(expectedMember);

        Response actualMember = resource.getMember(expectedMember.getUUID());

        assertThat(actualMember.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void whenWeGetOnANonExistentMember_WeGet404NotFound(){
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.findMember(expectedMember.getUUID())).thenReturn(null);

        Response actualMember = resource.getMember(expectedMember.getUUID());

        assertThat(actualMember.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenAnEmptyListOfMembers_getAllMembers_return404NotFound() {
        ItemsListingTO memberListingTO = new ItemsListingTO();
        Mockito.when(service.findAllMembers("0", "100", "", "", "")).thenReturn(memberListingTO);

        Response response = resource.getAllMembers(Integer.toString(0),Integer.toString(100), "", "", "");

        Mockito.verify(service, times(1)).findAllMembers("0", "100", "", "", "");
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

    @Test
    public void givenAListOfMembers_getAllMembers_return200OK() {
        ItemsListingTO memberListingTO = new ItemsListingTO();
        memberListingTO.setItems(Arrays.asList(new MemberTO()));
        Mockito.when(service.findAllMembers("0", "100", "", "", "")).thenReturn(memberListingTO);

        Response response = resource.getAllMembers(Integer.toString(0),Integer.toString(100), "", "", "");

        Mockito.verify(service, times(1)).findAllMembers("0", "100", "", "", "");
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAListOfMembers_getAllMembers_returnTheCorrectEntity() {
        ItemsListingTO memberListingTO = new ItemsListingTO();
        memberListingTO.setItems(Arrays.asList(new MemberTO()));
        Mockito.when(service.findAllMembers("0", "100", "", "city", "asc")).thenReturn(memberListingTO);

        Response response = resource.getAllMembers(Integer.toString(0),Integer.toString(100), "", "city", "asc");

        Mockito.verify(service, times(1)).findAllMembers("0", "100", "", "city", "asc");
        assertThat(response.getEntity()).isEqualTo(memberListingTO);
    }

    @Test
    public void givenAListOfMembers_getAllMembersWithoutParams_returnTheCorrectEntity() {
        ItemsListingTO memberListingTO = new ItemsListingTO();
        memberListingTO.setItems(Arrays.asList(new MemberTO()));
        Mockito.when(service.findAllMembers("", "", "", "", "")).thenReturn(memberListingTO);

        Response response = resource.getAllMembers("","", "", "", "");

        Mockito.verify(service, times(1)).findAllMembers("", "", "", "", "");
        assertThat(response.getEntity()).isEqualTo(memberListingTO);
    }

    @Test
    public void givenAListOfMembers_getAllMembersWithNegativeParams_returnTheCorrectEntity() {
        ItemsListingTO memberListingTO = new ItemsListingTO();
        memberListingTO.setItems(Arrays.asList(new MemberTO()));
        Mockito.when(service.findAllMembers("-1", "-5", "", "address", "asc")).thenReturn(memberListingTO);

        Response response = resource.getAllMembers("-1","-5", "", "address", "asc");

        Mockito.verify(service, times(1)).findAllMembers("-1", "-5", "", "address", "asc");
        assertThat(response.getEntity()).isEqualTo(memberListingTO);
    }
}
