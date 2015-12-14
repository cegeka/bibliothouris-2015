package unit;

import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
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

@RunWith(MockitoJUnitRunner.class)
public class BorrowHistoryServiceTest {

    @InjectMocks
    private BorrowHistoryService service;

    @Mock
    private BorrowHistoryRepository mockRepository;


    @Test(expected = ValidationException.class)
    public void givenABorrowHistoryItemWithEndDateBeforeThanStartDate_whenValidated_shouldThrowException() {
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createHistoryItemWithEndDateBeforeThanStartDate();

        service.createBorrowHistoryItem(new Book(), new Member(), borrowHistoryItem);
    }

    @Test
    public void givenValidBorrowHistoryItem_whenValidated_shouldReturnNewBorrowHistoryItem() {
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Member member = MemberTestFixture.createMember();

        Mockito.when(mockRepository.addBorrowedBook(book, member, borrowHistoryItem)).thenReturn(borrowHistoryItem);

        BorrowHistoryItem newBorrowHistoryItem = service.createBorrowHistoryItem(book, member, borrowHistoryItem);

        Assertions.assertThat(newBorrowHistoryItem).isEqualTo(borrowHistoryItem);
    }

}
