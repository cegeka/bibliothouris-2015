package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.application.valueobject.BooksFilterParams;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
        validateBorrowHistoryItem(book, member);
        BorrowHistoryItem borrowHistoryItem = BorrowHistoryItem.BorrowedHistoryBuilder.borrowedHistory()
                .withBook(book)
                .withMember(member)
                .withStartDate(LocalDate.now()).build();

        return borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);
    }

    private void validateBorrowHistoryItem(Book book, Member member) {
        if (book == null || member == null)
            throw new ValidationException("The borrow history item is invalid!");

        Long countCurrentlyBorrowedBooksByMember = bookRepository.countCurrentlyBorrowedBooksByMember(member.getUUID());
        if(countCurrentlyBorrowedBooksByMember >= 3)
            throw new ValidationException("Too many borrowed books!");
    }

    public BorrowHistoryItem updateBorrowHistoryItem(IntegerTO historyItemIdTO) {
        BorrowHistoryItem borrowHistoryItem = borrowHistoryRepository.findBorrowHistoryItemById(historyItemIdTO.getValue());
        if (borrowHistoryItem == null)
            return borrowHistoryItem;

        borrowHistoryItem.setEndDate(LocalDate.now());

        return borrowHistoryRepository.updateBorrowedBook(borrowHistoryItem);
    }

    @Transactional(readOnly = true)
    public ItemsListingTO<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUuid, PaginationParams pagination) {
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countBorrowedBooksByMember(memberUuid));

        return borrowHistoryRepository.findBorrowedBooksByMember(memberUuid, paginationParams);
    }

    @Transactional(readOnly = true)
    public Long countBorrowedBooksByMember(String memberUuid) {
        return borrowHistoryRepository.countBorrowedBooksByMember(memberUuid);
    }

    @Transactional(readOnly = true)
    public Long countBorrowedBooks(){
        return borrowHistoryRepository.countBorrowedBooks();
    }

    @Transactional
    public ItemsListingTO<DetailedBorrowHistoryTO> getActiveBorrowedBooks(PaginationParams pagination, SortParams sortParams) {
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countBorrowedBooks());

        return borrowHistoryRepository.getBorrowedBooks(paginationParams, sortParams);
    }

    @Transactional
    public ItemsListingTO<DetailedBorrowHistoryTO> getOverdueBooks(PaginationParams pagination, SortParams sortParams) {
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countBorrowedBooks());

        return borrowHistoryRepository.getOverdueBooks(paginationParams, sortParams);
    }
}
