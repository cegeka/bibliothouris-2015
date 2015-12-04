package unit;

import cgk.bibliothouris.learning.application.resource.AuthorResource;
import cgk.bibliothouris.learning.service.AuthorService;
import cgk.bibliothouris.learning.service.entity.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AuthorResourceTest {

    @InjectMocks
    private AuthorResource authorResource;

    @Mock
    private AuthorService mockAuthorService;


    @Test
    public void givenAListOfAuthors_findAllAuthors_return200OKResponse() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        Mockito.when(mockAuthorService.findAllAuthors()).thenReturn(authors);

        Response response = authorResource.getAllAuthors();

        Mockito.verify(mockAuthorService, times(1)).findAllAuthors();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAListOfAuthors_findAllAuthors_returnCorrectEntity() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        Mockito.when(mockAuthorService.findAllAuthors()).thenReturn(authors);

        Response response = authorResource.getAllAuthors();

        Mockito.verify(mockAuthorService, times(1)).findAllAuthors();
        assertThat(response.getEntity()).isEqualTo(authors);
    }

    @Test
    public void givenAnEmptyListOfAuthors_findAllAuthors_return404NotFound() {
        List<Author> authors = new ArrayList<>();
        Mockito.when(mockAuthorService.findAllAuthors()).thenReturn(authors);

        Response response = authorResource.getAllAuthors();

        Mockito.verify(mockAuthorService, times(1)).findAllAuthors();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }
}
