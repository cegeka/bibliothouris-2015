package fixture;

import cgk.bibliothouris.learning.service.entity.BorrowedHistory;
import cgk.bibliothouris.learning.service.entity.Member;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.UUID;

public class BorrowedHistoryFixture {

    public static BorrowedHistory createHistoryItem() {
        LocalDate date = LocalDate.of(2015,4,4);
        return BorrowedHistory.BorrowedHistoryBuilder.borrowedHistory()
                .withStartDate(date)
                .build();
    }
}
