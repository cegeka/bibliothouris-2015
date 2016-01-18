package unit;

import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.repository.AuthorRepository;
import cgk.bibliothouris.learning.service.AuthorService;
import cgk.bibliothouris.learning.service.entity.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    private AuthorRepository mockRepository;

    @Test
    public void givenOneAuthor_findAllAuthors_returnsTheAuthor() {
        List<Author> expectedAuthors = new ArrayList<>();
        Mockito.when(mockRepository.findAllAuthors()).thenReturn(expectedAuthors);

        List<Author> foundAuthors = service.findAllAuthors();

        assertThat(foundAuthors).isEqualTo(expectedAuthors);
    }

    @Test
    public void givenOneAuthor_findAllBookAuthorFirstName_returnsTheAuthor() {
        List<StringTO> expectedAuthors = new ArrayList<>();
        Mockito.when(mockRepository.findAllBookAuthorFirstName()).thenReturn(expectedAuthors);

        List<Author> foundAuthors = service.findAllAuthors();

        assertThat(foundAuthors).isEqualTo(expectedAuthors);
    }

    @Test
    public void givenOneAuthor_findAllBookAuthorLastName_returnsTheAuthor() {
        List<StringTO> expectedAuthors = new ArrayList<>();
        Mockito.when(mockRepository.findAllBookAuthorLastName()).thenReturn(expectedAuthors);

        List<Author> foundAuthors = service.findAllAuthors();

        assertThat(foundAuthors).isEqualTo(expectedAuthors);
    }


}
