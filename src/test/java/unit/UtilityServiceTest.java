package unit;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.service.BiblioUtilityService;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UtilityServiceTest {

    @Test(expected = ValidationException.class)
    public void givenNegativeStartPaginationParameter_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("-1", "10");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test(expected = ValidationException.class)
    public void givenNegativeEndPaginationParameter_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("0", "-10");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test(expected = ValidationException.class)
    public void givenNegativePaginationParameters_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("-1", "-10");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test(expected = ValidationException.class)
    public void givenNonNumericStartPaginationParameter_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("string", "-10");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test(expected = ValidationException.class)
    public void givenNonNumericEndPaginationParameter_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("0", "string");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test(expected = ValidationException.class)
    public void givenNonNumericPaginationParameters_whenPaginationParametersAreValidated_thenExceptionIsThrown() {
        PaginationParams paginationParams = new PaginationParams("string", "string");

        BiblioUtilityService.validatePaginationParams(paginationParams);
    }

    @Test
    public void givenNoPaginationParameters_findPaginationParameters_returnCorrectParameters() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        expectedMemberListingTO.setItemsCount(10L);

        PaginationParams findParams = BiblioUtilityService.findPaginationParameters(new PaginationParams(), () -> expectedMemberListingTO.getItemsCount());

        assertThat(findParams.getStart()).isEqualTo("0");
        assertThat(findParams.getEnd()).isEqualTo(expectedMemberListingTO.getItemsCount().toString());
    }

    @Test
    public void givenPaginationParameters_findPaginationParameters_returnCorrectParameters() {
        PaginationParams findParams = BiblioUtilityService.findPaginationParameters(new PaginationParams("1", "5"), null);

        assertThat(findParams.getStart()).isEqualTo("1");
        assertThat(findParams.getEnd()).isEqualTo("5");
    }
}
