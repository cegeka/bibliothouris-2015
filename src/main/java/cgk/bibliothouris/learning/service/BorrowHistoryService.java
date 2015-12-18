package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
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
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BorrowHistoryService extends BiblioService{

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
                .withStartDate(borrowHistoryItemTO.getDate()).build();

        return borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);
    }

    @Transactional(readOnly = true)
    public List<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUuid, String start, String end) {
        Pair<Integer, Integer> paginationParams = findPaginationParameters(start, end, () -> countBorrowedBooksByMember(memberUuid));

        return borrowHistoryRepository.findBorrowedBooksByMember(memberUuid, paginationParams.getFirst(), paginationParams.getSecond());
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
        if (book == null || member == null)
            throw new ValidationException("The borrow history item is invalid!");

        Long countCurrentlyBorrowedBooksByMember = bookRepository.countCurrentlyBorrowedBooksByMember(member.getUUID());
        if(countCurrentlyBorrowedBooksByMember >= 3)
            throw new ValidationException("Too many borrowed books!");
    }

    @Transactional
    public List<DetailedBorrowHistoryTO> getActiveBorrowedBooks(String start, String end, String sort, String order) {
        Pair<Integer, Integer> paginationParams = findPaginationParameters(start, end, () -> countBorrowedBooks());

        return borrowHistoryRepository.getBorrowedBooks(paginationParams.getFirst(), paginationParams.getSecond(), sort, order);
    }

    @Transactional
    public BookListingTO<DetailedBorrowHistoryTO> getOverdueBooks(String start, String end, String sort, String order) {
        Pair<Integer, Integer> paginationParams = findPaginationParameters(start, end, () -> countBorrowedBooks());

        return borrowHistoryRepository.getOverdueBooks(paginationParams.getFirst(), paginationParams.getSecond(), sort, order);
    }
}
