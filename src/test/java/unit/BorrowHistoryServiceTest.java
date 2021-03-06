package unit;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BorrowedHistoryFixture;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BorrowHistoryServiceTest {

    @InjectMocks
    private BorrowHistoryService service;

    @Mock
    private BorrowHistoryRepository borrowHistoryRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void givenValidBorrowHistoryItemTO_whenValidated_shouldReturnNewBorrowHistoryItem() {
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTO();
        when(bookRepository.findBookById(borrowHistoryItemTO.getBookId())).thenReturn(borrowHistoryItem.getBook());
        when(memberRepository.getMember(borrowHistoryItemTO.getMemberUuid())).thenReturn(borrowHistoryItem.getMember());
        when(borrowHistoryRepository.addBorrowedBook(borrowHistoryItem)).thenReturn(borrowHistoryItem);

        BorrowHistoryItem newBorrowHistoryItem = service.createBorrowHistoryItem(borrowHistoryItemTO);

        Mockito.verify(borrowHistoryRepository, Mockito.times(1)).addBorrowedBook(borrowHistoryItem);
        assertThat(newBorrowHistoryItem).isEqualTo(borrowHistoryItem);
    }

    @Test
    public void givenAMemberId_whenCountBorrowBooksByMember_shouldReturnTheCorrectCount() {
        when(borrowHistoryRepository.countBorrowedBooksByMember("uuid")).thenReturn(1L);

        Long count = service.countBorrowedBooksByMember("uuid");

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAMemberIdAndPositivePaginationParameters_whenFindBorrowBooksByMember_shouldReturnTheCorrectListWithBorrowedBooks() {
        ItemsListingTO<MemberBorrowHistoryTO> expectedBorrowedHistoryItems = new ItemsListingTO<>();
        Mockito.when(borrowHistoryRepository.findBorrowedBooksByMember("uuid", 0, 10)).thenReturn(expectedBorrowedHistoryItems);

        ItemsListingTO<MemberBorrowHistoryTO> foundBorrowedHistoryItems = service.findBorrowedBooksByMember("uuid", "0", "10");

        assertThat(foundBorrowedHistoryItems).isEqualTo(expectedBorrowedHistoryItems);
    }


    @Test
    public void givenAMemberIdAndNegativePaginationParameters_whenFindBorrowBooksByMember_shouldReturnTheCorrectListWithBorrowedBooks() {
        ItemsListingTO<MemberBorrowHistoryTO> expectedBorrowedHistoryItems = new ItemsListingTO<>();
        Mockito.when(borrowHistoryRepository.findBorrowedBooksByMember("uuid", 0, 0)).thenReturn(expectedBorrowedHistoryItems);

        ItemsListingTO<MemberBorrowHistoryTO> foundBorrowedHistoryItems = service.findBorrowedBooksByMember("uuid", "-3", "-1");

        assertThat(foundBorrowedHistoryItems).isEqualTo(expectedBorrowedHistoryItems);
    }

    @Test
    public void givenAPopulatedHistory_whenWeRetrieveIt_weGetThatHistory(){
        ItemsListingTO<DetailedBorrowHistoryTO> borrowHistoryTO = new ItemsListingTO<>();
        Mockito.when(borrowHistoryRepository.getBorrowedBooks(1, 10, "title,isbn,date", "asc,asc,desc")).thenReturn(borrowHistoryTO);

        ItemsListingTO<DetailedBorrowHistoryTO> foundGlobalBorrowedHistoryItems = service.getActiveBorrowedBooks("1", "10", "title,isbn,date","asc,asc,desc");

        Assertions.assertThat(foundGlobalBorrowedHistoryItems).isEqualTo(borrowHistoryTO);
    }

    @Test
    public void givenAPopulatedHistory_whenWeRetrieveOverdueBooks_shouldReturnTheOverdueBooks(){
        ItemsListingTO<DetailedBorrowHistoryTO> overdueBooks = new ItemsListingTO<>();
        when(borrowHistoryRepository.getOverdueBooks(1, 10, "title", "asc")).thenReturn(overdueBooks);

        ItemsListingTO<DetailedBorrowHistoryTO> foundOverdueBooks = service.getOverdueBooks("1", "10", "title", "asc");

        assertThat(foundOverdueBooks).isEqualTo(overdueBooks);
    }

    @Test
    public void givenABorrowedHistoryItemId_whenWeUpdateTheBorrowedHistoryItem_shouldReturnTheUpdatedItem(){
        BorrowHistoryItem borrowHistoryItem = new BorrowHistoryItem();
        borrowHistoryItem.setId(1);
        when(borrowHistoryRepository.updateBorrowedBook(borrowHistoryItem)).thenReturn(borrowHistoryItem);
        when(borrowHistoryRepository.findBorrowHistoryItemById(borrowHistoryItem.getId())).thenReturn(borrowHistoryItem);

        BorrowHistoryItem updatedBorrowHistoryItem = service.updateBorrowHistoryItem(new IntegerTO(1));

        assertThat(updatedBorrowHistoryItem).isEqualTo(borrowHistoryItem);
    }
}
