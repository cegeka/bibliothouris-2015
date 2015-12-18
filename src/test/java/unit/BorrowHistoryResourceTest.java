package unit;

import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BorrowedHistoryFixture;
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

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returns404NOTFOUND() {
        List<MemberBorrowHistoryTO> borrowHistoryTOs = new ArrayList<>();
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returns200OK() {
        List<MemberBorrowHistoryTO> borrowHistoryTOs = Arrays.asList(new MemberBorrowHistoryTO());
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAMemberUuid_findBorrowedHistoryItems_returnsTheCorrectEntity() {
        List<MemberBorrowHistoryTO> borrowHistoryTOs = Arrays.asList(new MemberBorrowHistoryTO());
        Mockito.when(service.findBorrowedBooksByMember("uuid", "0", "10")).thenReturn(borrowHistoryTOs);

        Response response = resource.getBorrowHistoryItemsByMember("uuid", "0", "10");

        assertThat(response.getEntity()).isEqualTo(borrowHistoryTOs);
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet200OK(){
        List<DetailedBorrowHistoryTO> borrowHistoryList= BorrowedHistoryFixture.createDetailedBorrowHistoryTOList();

        Mockito.when(service.getActiveBorrowedBooks("1", "10", "title,isbn,date","asc,asc,desc")).thenReturn(borrowHistoryList);

        Response response = resource.getAllHistoryItems("1", "10", "title,isbn,date","asc,asc,desc");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenValidBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGetThemALl(){
        List<DetailedBorrowHistoryTO> borrowHistoryList= BorrowedHistoryFixture.createDetailedBorrowHistoryTOList();

        Mockito.when(service.getActiveBorrowedBooks("1", "10", "title,isbn,date","asc,asc,desc")).thenReturn(borrowHistoryList);

        Response response = resource.getAllHistoryItems("1", "10", "title,isbn,date","asc,asc,desc");

        Assertions.assertThat(response.getEntity()).isEqualTo(borrowHistoryList);
    }

    @Test
    public void givenEmBorrowHistoryItems_whenWeRetrieveThemAll_thenWeGet404NotFound(){
        List<DetailedBorrowHistoryTO> borrowHistoryList= BorrowedHistoryFixture.createEmptyDetailedBorrowHistoryTOList();

        Mockito.when(service.getActiveBorrowedBooks("1", "10","","")).thenReturn(borrowHistoryList);

        Response response = resource.getAllHistoryItems("1", "10","","");

        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }



}
