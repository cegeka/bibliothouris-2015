package fixture;

import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.GlobalBorrowHistoryTO;
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
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 4))
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

    public static List<GlobalBorrowHistoryTO> createGlobalBorrowHistoryTOList() {
        List<GlobalBorrowHistoryTO> list = new ArrayList<>();
        BorrowHistoryItem historyItem = createAvailableHistoryItem();
        GlobalBorrowHistoryTO gbh = GlobalBorrowHistoryTO
                .GlobalBorrowHistoryTOBuilder
                .globalBorrowHistoryTO()
                .withAuthors(historyItem.getBook().getAuthors())
                .withIsbn(historyItem.getBook().getIsbn())
                .withTitle(historyItem.getBook().getTitle())
                .withStartLendDate(LocalDate.of(2015, Month.DECEMBER, 4))
                .withBorrowerFirstName(historyItem.getMember().getFirstName())
                .withBorrowerLastName(historyItem.getMember().getLastName())
                .withBorrowerUUID(historyItem.getMember().getUUID())
                .build();

        list.add(gbh);
        return list;
    }

    public static List<GlobalBorrowHistoryTO> createEmptyGlobalBorrowHistoryTOList() {
        List<GlobalBorrowHistoryTO> list = new ArrayList<>();

        return list;
    }
}
