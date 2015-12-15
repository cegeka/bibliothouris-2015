package unit;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.BorrowHistoryService;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
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

    private BorrowHistoryItemTO invalidBorrowHistoryItemTO;

    @Before
    public void setUp() {
        invalidBorrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTOWithEndDateBeforeThanStartDate();
    }

    @Test(expected = ValidationException.class)
    public void givenABorrowHistoryItemTOWithEndDateBeforeThanStartDate_whenValidated_shouldThrowException() {
        service.createBorrowHistoryItem(invalidBorrowHistoryItemTO);
    }

    @Test
    public void givenValidBorrowHistoryItemTO_whenValidated_shouldReturnNewBorrowHistoryItem() {
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowedHistoryFixture.createHistoryItemTO();
        Mockito.when(bookRepository.findBookById(borrowHistoryItemTO.getBookId())).thenReturn(borrowHistoryItem.getBook());
        Mockito.when(memberRepository.getMember(borrowHistoryItemTO.getMemberUuid())).thenReturn(borrowHistoryItem.getMember());
        Mockito.when(borrowHistoryRepository.addBorrowedBook(borrowHistoryItem)).thenReturn(borrowHistoryItem);

        BorrowHistoryItem newBorrowHistoryItem = service.createBorrowHistoryItem(borrowHistoryItemTO);

        Mockito.verify(borrowHistoryRepository, Mockito.times(1)).addBorrowedBook(borrowHistoryItem);
        Assertions.assertThat(newBorrowHistoryItem).isEqualTo(borrowHistoryItem);
    }

    @Test
    public void givenAMemberId_whenCountBorrowBooksByMember_shouldReturnTheCorrectCount() {
        Mockito.when(borrowHistoryRepository.countBorrowedBooksByMember("uuid")).thenReturn(1L);

        Long count = service.countBorrowedBooksByMember("uuid");

        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAMemberIdAndPositivePaginationParameters_whenFindBorrowBooksByMember_shouldReturnTheCorrectListWithBorrowedBooks() {
        List<MemberBorrowHistoryTO> expectedBorrowedHistoryItems = new ArrayList<>();
        Mockito.when(borrowHistoryRepository.findBorrowedBooksByMember("uuid", 0, 10)).thenReturn(expectedBorrowedHistoryItems);

        List<MemberBorrowHistoryTO> foundBorrowedHistoryItems = service.findBorrowedBooksByMember("uuid", "0", "10");

        Assertions.assertThat(foundBorrowedHistoryItems).isEqualTo(expectedBorrowedHistoryItems);
    }


    @Test
    public void givenAMemberIdAndNegativePaginationParameters_whenFindBorrowBooksByMember_shouldReturnTheCorrectListWithBorrowedBooks() {
        List<MemberBorrowHistoryTO> expectedBorrowedHistoryItems = new ArrayList<>();
        Mockito.when(borrowHistoryRepository.findBorrowedBooksByMember("uuid", 0, 0)).thenReturn(expectedBorrowedHistoryItems);

        List<MemberBorrowHistoryTO> foundBorrowedHistoryItems = service.findBorrowedBooksByMember("uuid", "-3", "-1");

        Assertions.assertThat(foundBorrowedHistoryItems).isEqualTo(expectedBorrowedHistoryItems);
    }
}
