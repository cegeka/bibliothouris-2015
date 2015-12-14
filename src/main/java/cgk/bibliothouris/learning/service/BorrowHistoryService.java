package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
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
    private BorrowHistoryRepository repository;

    public BorrowHistoryItem createBorrowHistoryItem(Book book, Member member, BorrowHistoryItem borrowHistoryItem) {
        validateBorrowHistoryItem(borrowHistoryItem);

        return repository.addBorrowedBook(book, member, borrowHistoryItem);
    }

    private void validateBorrowHistoryItem(BorrowHistoryItem borrowHistoryItem) {
        if (borrowHistoryItem.getEndDate() != null && borrowHistoryItem.getEndDate().isBefore(borrowHistoryItem.getStartDate()))
            throw new ValidationException("End date is before start date!");
    }
}
