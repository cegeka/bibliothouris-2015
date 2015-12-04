package unit;

import cgk.bibliothouris.learning.repository.AuthorRepository;
import cgk.bibliothouris.learning.repository.CategoryRepository;
import cgk.bibliothouris.learning.service.AuthorService;
import cgk.bibliothouris.learning.service.CategoryService;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BookCategory;
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
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository mockRepository;

    @Test
    public void givenOneCategory_findAllCategories_returnsTheCategory(){
        List<BookCategory> expectedCategories = new ArrayList<>();
        Mockito.when(mockRepository.findAllCategories()).thenReturn(expectedCategories);

        List<BookCategory> foundAuthors = service.findAllCategories();

        assertThat(foundAuthors).isEqualTo(expectedCategories);
    }
}
