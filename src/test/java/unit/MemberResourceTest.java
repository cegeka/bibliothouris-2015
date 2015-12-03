package unit;

import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.MemberTestFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class MemberResourceTest {

    @InjectMocks
    private MemberResource resource;

    @Mock
    private MemberService service;

    @Test
    public void whenWePostMember_WeGetUUIDBack() {
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getEntity()).isEqualTo(expectedMember);
    }

    @Test
    public void whenWePostMember_WeGetOKStatus() {
        Member expectedMember = MemberTestFixture.createMember();

        Mockito.when(service.createMember(expectedMember)).thenReturn(expectedMember);

        Response response = resource.addMember(expectedMember);

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
    }

}
