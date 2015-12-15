package unit;

import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BookTestFixture;
import fixture.BorrowedHistoryFixture;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BorrowHistoryResourceTest {

    @InjectMocks
    private BorrowHistoryResource resource;

    @Mock
    private BorrowHistoryService service;

    @Mock
    private UriInfo uriInfo;

    @Test
    public void givenAValidBorrowHistoryItem_createANewBorrowItem_returns200OK() {
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTO();
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
        Mockito.when(service.createBorrowHistoryItem(borrowHistoryItemTO)).thenReturn(borrowHistoryItem);

        Response response = resource.borrowBook(borrowHistoryItemTO);

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAValidBorrowHistoryItem_createANewBorrowItem_returnsLinkToTheNewItem() {
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTO();
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
        borrowHistoryItem.setId(1);
        Mockito.when(service.createBorrowHistoryItem(borrowHistoryItemTO)).thenReturn(borrowHistoryItem);
        Mockito.when(uriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/webapi/borrowed_books"));

        Response response = resource.borrowBook(borrowHistoryItemTO);

        assertThat(response.getLocation()).isEqualTo(URI.create("http://localhost:8080/webapi/borrowed_books/1"));
    }

    @Test
    public void givenAnInvalidBorrowHistoryItem_createANewBorrowItem_returns400BADREQUEST() {
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTOWithEndDateBeforeThanStartDate();
        Mockito.when(service.createBorrowHistoryItem(borrowHistoryItemTO)).thenThrow(ValidationException.class);

        Response response = resource.borrowBook(borrowHistoryItemTO);

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
