package by.epam.ivanchenko.util;

import by.epam.ivanchenko.dao.BookDAO;
import by.epam.ivanchenko.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.findBook(book.getBookName(), book.getAuthor()).isPresent()) {
                errors.rejectValue("bookName", "", "Такая книга уже существует!");
            }
        }
    }

