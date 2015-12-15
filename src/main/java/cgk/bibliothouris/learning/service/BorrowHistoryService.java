package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BookRepositoryJPA;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        validateBorrowHistoryItem(borrowHistoryItemTO);

        Book book = bookRepository.findBookById(borrowHistoryItemTO.getBookId());
        Member member = memberRepository.getMember(borrowHistoryItemTO.getMemberUuid());
        BorrowHistoryItem borrowHistoryItem = BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                                                                .withBook(book)
                                                                .withMember(member)
                                                                .withStartDate(borrowHistoryItemTO.getStartDate())
                                                                .withEndDate(borrowHistoryItemTO.getEndDate()).build();

        return borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);
    }

    private void validateBorrowHistoryItem(BorrowHistoryItemTO borrowHistoryItemTO) {
        if (borrowHistoryItemTO.getEndDate() != null && borrowHistoryItemTO.getEndDate().isBefore(borrowHistoryItemTO.getStartDate()))
            throw new ValidationException("End date is before start date!");
    }
}
