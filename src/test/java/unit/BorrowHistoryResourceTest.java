package unit;

import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BorrowHistoryResourceTest {

    @InjectMocks
    private BorrowHistoryResource resource;

    @Mock
    private BorrowHistoryService service;

    @Mock
    private UriInfo uriInfo;

    private PaginationParams pagination = new PaginationParams("0", "10");
    private String start = "0", end = "10";

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
        ItemsListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        Mockito.when(service.findBorrowedBooksByMember("uuid", pagination)).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", start, end);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returns200OK() {
        ItemsListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        borrowHistoryTOs.setItems(Arrays.asList(new MemberBorrowHistoryTO()));
        Mockito.when(service.findBorrowedBooksByMember("uuid", pagination)).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", start, end);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returnsTheCorrectEntity() {
        ItemsListingTO<MemberBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        borrowHistoryTOs.setItems(Arrays.asList(new MemberBorrowHistoryTO()));
        Mockito.when(service.findBorrowedBooksByMember("uuid", pagination)).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", start, end);

        assertThat(response.getEntity()).isEqualTo(borrowHistoryTOs);
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet200OK(){
        ItemsListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        borrowHistoryTOs.setItems(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getActiveBorrowedBooks(pagination, new SortParams("title", "asc"))).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems(start, end, "title", "asc");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGetThemAll(){
        ItemsListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        borrowHistoryTOs.setItems(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getActiveBorrowedBooks(pagination, new SortParams("title", "asc"))).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems(start, end, "title","asc");

        Assertions.assertThat(response.getEntity()).isEqualTo(borrowHistoryTOs);
    }

    @Test
    public void givenNoBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet404NotFound(){
        ItemsListingTO<DetailedBorrowHistoryTO> borrowHistoryTOs = new ItemsListingTO<>();
        Mockito.when(service.getActiveBorrowedBooks(pagination, new SortParams("", ""))).thenReturn(borrowHistoryTOs);

        Response response = resource.getAllHistoryItems(start, end, "", "");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenListOfOverdueBooks_getOverdueBooks_returns200OK(){
        ItemsListingTO<DetailedBorrowHistoryTO> overdueBooks = new ItemsListingTO<>();
        overdueBooks.setItems(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getOverdueBooks(pagination, new SortParams("title", "asc"))).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks(start, end, "title", "asc");

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenListOfOverdueBooks_getOverdueBooks_returnsTheListOfOverdueBooks(){
        ItemsListingTO<DetailedBorrowHistoryTO> overdueBooks = new ItemsListingTO<>();
        overdueBooks.setItems(Arrays.asList(new DetailedBorrowHistoryTO()));
        Mockito.when(service.getOverdueBooks(pagination, new SortParams("title", "asc"))).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks(start, end, "title", "asc");

        Assertions.assertThat(response.getEntity()).isEqualTo(overdueBooks);
    }

    @Test
    public void givenNoOverdueBooks_getOverdueBooks_returns404NotFound(){
        ItemsListingTO<DetailedBorrowHistoryTO> overdueBooks = new ItemsListingTO<>();
        Mockito.when(service.getOverdueBooks(pagination, new SortParams("", ""))).thenReturn(overdueBooks);

        Response response = resource.getOverdueBooks(start, end, "", "");

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAnInvalidBorrowHistoryItemId_updateBorrowHistoryItem_returns404NotFound(){
        IntegerTO borrowHistoryItemIdTO = new IntegerTO();
        Mockito.when(service.updateBorrowHistoryItem(borrowHistoryItemIdTO)).thenReturn(null);

        Response response = resource.returnBook(borrowHistoryItemIdTO);

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAValidBorrowHistoryItemId_updateBorrowHistoryItem_returns200OK(){
        IntegerTO borrowHistoryItemIdTO = new IntegerTO();
        Mockito.when(service.updateBorrowHistoryItem(borrowHistoryItemIdTO)).thenReturn(new BorrowHistoryItem());

        Response response = resource.returnBook(borrowHistoryItemIdTO);

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

}
