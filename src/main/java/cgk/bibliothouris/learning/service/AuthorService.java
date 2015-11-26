package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.repository.AuthorRepository;
import cgk.bibliothouris.learning.service.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAllAuthors(){
        return authorRepository.findAllAuthors();
    }
}
