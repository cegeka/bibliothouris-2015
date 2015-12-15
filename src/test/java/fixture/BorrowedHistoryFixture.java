package fixture;

import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;

import java.time.LocalDate;
import java.time.Month;

public class BorrowedHistoryFixture {

    public static BorrowHistoryItem createHistoryItem() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .build();
    }

    public static BorrowHistoryItem createHistoryItemWithEndDateBeforeThanStartDate() {
        return BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(BookTestFixture.createBookWithOneAuthorAndOneCategory())
                .withMember(MemberTestFixture.createMember())
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .withEndDate(LocalDate.of(2015, Month.JANUARY, 5))
                .build();
    }

    public static BorrowHistoryItemTO createHistoryItemTOWithEndDateBeforeThanStartDate() {
        return BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .withEndDate(LocalDate.of(2015, Month.JANUARY, 5))
                .build();
    }

    public static BorrowHistoryItemTO createHistoryItemTO() {
        return BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                .withBookId(1)
                .withMemberUuid("uuid")
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .build();
    }
}
