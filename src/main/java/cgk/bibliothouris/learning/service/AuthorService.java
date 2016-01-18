package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.repository.AuthorRepository;
import cgk.bibliothouris.learning.service.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<Author> findAllAuthors(){
        return authorRepository.findAllAuthors();
    }

    @Transactional(readOnly = true)
    public List<StringTO> findAllBookAuthorFirstName() {
        return authorRepository.findAllBookAuthorFirstName();
    }

    @Transactional(readOnly = true)
    public List<StringTO> findAllBookAuthorLastName() {
        return authorRepository.findAllBookAuthorLastName();
    }
}
