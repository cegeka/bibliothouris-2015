package unit;

import cgk.bibliothouris.learning.application.resource.CategoryResource;
import cgk.bibliothouris.learning.service.CategoryService;
import cgk.bibliothouris.learning.service.entity.BookCategory;
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
public class CategoryResourceTest {

    @InjectMocks
    private CategoryResource categoryResource;

    @Mock
    private CategoryService mockCategoryService;

    @Test
    public void givenAListOfCategories_findAllCategories_return200OKResponse(){
        List<BookCategory> categories = new ArrayList<>();
        categories.add(new BookCategory());
        Mockito.when(mockCategoryService.findAllCategories()).thenReturn(categories);

        Response response = categoryResource.findAllCategories();

        Mockito.verify(mockCategoryService, times(1)).findAllCategories();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAListOfCategories_findAllCategories_returnCorrectEntity(){
        List<BookCategory> categories = new ArrayList<>();
        categories.add(new BookCategory());
        Mockito.when(mockCategoryService.findAllCategories()).thenReturn(categories);

        Response response = categoryResource.findAllCategories();

        Mockito.verify(mockCategoryService, times(1)).findAllCategories();
        assertThat(response.getEntity()).isEqualTo(categories);
    }

    @Test
    public void givenAnEmptyListOfCategories_findAllCategories_return404NotFound(){
        List<BookCategory> categories = new ArrayList<>();
        Mockito.when(mockCategoryService.findAllCategories()).thenReturn(categories);

        Response response = categoryResource.findAllCategories();

        Mockito.verify(mockCategoryService, times(1)).findAllCategories();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }
}
