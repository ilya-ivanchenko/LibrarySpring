package by.epam.ivanchenko.util;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookService.getByNameAndAuthor(book.getBookName(), book.getAuthor()).isPresent()) {
                errors.rejectValue("bookName", "", "Такая книга уже существует!");
            }
        }
    }

