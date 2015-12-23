package unit;

import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import fixture.BorrowedHistoryFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createAvailableHistoryItem();
        Mockito.when(service.createBorrowHistoryItem(borrowHistoryItemTO)).thenReturn(borrowHistoryItem);

        Response response = resource.borrowBook(borrowHistoryItemTO);

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAValidBorrowHistoryItem_createANewBorrowItem_returnsLinkToTheNewItem() {
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTO();
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createAvailableHistoryItem();
        borrowHistoryItem.setId(1);
        Mockito.when(service.createBorrowHistoryItem(borrowHistoryItemTO)).thenReturn(borrowHistoryItem);
        Mockito.when(uriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/webapi/borrowed_books"));

        Response response = resource.borrowBook(borrowHistoryItemTO);

        assertThat(response.getLocation()).isEqualTo(URI.create("http://localhost:8080/webapi/borrowed_books/1"));
    }

    @Test
    public void givenAMemberUuid_countBorrowedHistoryItems_returns200OK() {
        Mockito.when(service.countBorrowedBooksByMember("uuid")).thenReturn(1L);

        Response response = resource.getBorrowHistoryItemsByMemberCount("uuid");

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAMemberUuid_countBorrowedHistoryItems_returnsTheCorrectSize() {
        Mockito.when(service.countBorrowedBooksByMember("uuid")).thenReturn(1L);

        Response response = resource.getBorrowHistoryItemsByMemberCount("uuid");

        Assertions.assertThat(response.getEntity()).isEqualTo(1L);
    }

    //TODO verify  that this test is correct given the fact there is no getBooks() attached to findBorrowedBooksByMember
    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returns404NOTFOUND() {
        BookListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returns200OK() {
        BookListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        borrowHistoryTOs.setBooks(Arrays.asList(new MemberBorrowHistoryTO()));
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returnsTheCorrectEntity() {
        BookListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        borrowHistoryTOs.setBooks(Arrays.asList(new MemberBorrowHistoryTO()));
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getEntity()).isEqualTo(borrowHistoryTOs);
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet200OK(){
        BookListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        borrowHistoryTOs.setBooks(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getActiveBorrowedBooks("1", "10", "title,isbn,date","asc,asc,desc")).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems("1", "10", "title,isbn,date","asc,asc,desc");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGetThemALl(){
        BookListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        borrowHistoryTOs.setBooks(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getActiveBorrowedBooks("1", "10", "title,isbn,date", "asc,asc,desc")).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems("1", "10", "title,isbn,date","asc,asc,desc");

        Assertions.assertThat(response.getEntity()).isEqualTo(borrowHistoryTOs);
    }

    @Test
    public void givenNoBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet404NotFound(){
        BookListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new BookListingTO<>();
        Mockito.when(service.getActiveBorrowedBooks("1", "10", "","")).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems("1", "10","","");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenListOfOverdueBooks_getOverdueBooks_returns200OK(){
        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = new BookListingTO<>();
        overdueBooks.setBooks(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getOverdueBooks("0", "10", "title", "asc")).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks("0", "10", "title", "asc");

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenListOfOverdueBooks_getOverdueBooks_returnsTheListOfOverdueBooks(){
        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = new BookListingTO<>();
        overdueBooks.setBooks(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getOverdueBooks("0", "10", "title", "asc")).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks("0", "10", "title", "asc");

        Assertions.assertThat(response.getEntity()).isEqualTo(overdueBooks);
    }

    @Test
    public void givenNoOverdueBooks_getOverdueBooks_returns404NotFound(){
        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = new BookListingTO<>();
        Mockito.when(service.getOverdueBooks("0", "10", "", "")).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks("0", "10", "", "");

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

}
