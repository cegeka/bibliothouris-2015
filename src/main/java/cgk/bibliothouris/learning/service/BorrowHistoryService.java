package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BorrowHistoryService {

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BorrowHistoryItem createBorrowHistoryItem(BorrowHistoryItemTO borrowHistoryItemTO) {

        Book book = bookRepository.findBookById(borrowHistoryItemTO.getBookId());
        Member member = memberRepository.getMember(borrowHistoryItemTO.getMemberUuid());
        validateBorrowHistoryItem(borrowHistoryItemTO, book, member);
        BorrowHistoryItem borrowHistoryItem = BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(book)
                .withMember(member)
                .withStartDate(borrowHistoryItemTO.getStartDate())
                .withEndDate(borrowHistoryItemTO.getEndDate()).build();

        return borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);
    }

    @Transactional(readOnly = true)
    public List<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUuid, String start, String end) {
        Integer startPosition, endPosition;

        if (start == null || isNegative(start))
            startPosition = 0;
        else
            startPosition = Integer.valueOf(start);

        if (end == null || isNegative(end))
            endPosition = countBorrowedBooksByMember(memberUuid).intValue();
        else
            endPosition = Integer.valueOf(end);

        return borrowHistoryRepository.findBorrowedBooksByMember(memberUuid, startPosition, endPosition);
    }

    @Transactional(readOnly = true)
    public Long countBorrowedBooksByMember(String memberUuid) {
        return borrowHistoryRepository.countBorrowedBooksByMember(memberUuid);
    }

    @Transactional(readOnly = true)
    public Long countBorrowedBooks(){
        return borrowHistoryRepository.countBorrowedBooks();
    }

    private void validateBorrowHistoryItem(BorrowHistoryItemTO borrowHistoryItemTO, Book book, Member member) {
        Boolean isEndDateAfterStartDate = false;
        Boolean isDateInvalid = borrowHistoryItemTO.getEndDate() == null;
        if (!isDateInvalid) {
            isEndDateAfterStartDate = borrowHistoryItemTO.getEndDate().isBefore(borrowHistoryItemTO.getStartDate());
        }
        Boolean isBookInvalid = (book == null);
        Boolean isMemberInvalid = (member == null);
        if (isEndDateAfterStartDate || isBookInvalid || isMemberInvalid)
            throw new ValidationException("The borrow history item is invalid!");
    }

    private boolean isNegative(String number) {
        try {
            if (Integer.parseInt(number) < 0)
                return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public List<DetailedBorrowHistoryTO> getActiveBorrowedBooks(String start, String end) {
        Integer startPosition, endPosition;

        if (start == null || isNegative(start))
            startPosition = 0;
        else
            startPosition = Integer.valueOf(start);

        if (end == null || isNegative(end))
            endPosition = Integer.valueOf(countBorrowedBooks().intValue());
        else
            endPosition = Integer.valueOf(end);

        return borrowHistoryRepository.getBorrowedBooks(startPosition, endPosition);
    }
}
