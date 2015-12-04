package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.repository.CategoryRepository;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<BookCategory> findAllCategories(){
        return categoryRepository.findAllCategories();
    }

}
