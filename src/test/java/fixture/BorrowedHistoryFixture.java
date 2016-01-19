package fixture;

import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BorrowedHistoryFixture {

    public static BorrowHistoryItem createBorrowedHistoryItem() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.now())
                .build();
    }

    public static BorrowHistoryItem createOverdueHistoryItemInPresent() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.now().minusDays(BorrowHistoryRepository.ALLOWED_BORROW_DAYS_NUMBER.longValue() + 1))
                .build();
    }

    public static BorrowHistoryItem createOverdueHistoryItemInThePast() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.now().minusDays(BorrowHistoryRepository.ALLOWED_BORROW_DAYS_NUMBER.longValue() + 1))
                .withEndDate(LocalDate.now())
                .build();
    }

    public static BorrowHistoryItem createAvailableHistoryItem() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .withEndDate(LocalDate.of(2015, Month.DECEMBER, 5))
                .build();
    }

    public static BorrowHistoryItemTO createHistoryItemTO() {
        return BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                .withBookId(1)
                .withMemberUuid("uuid")
                .withStartDate(LocalDate.now())
                .build();
    }
}
