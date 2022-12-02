package by.epam.ivanchenko.service;


import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        }
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

     public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
         if (sortByYear) {
             return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();  // getContent - получение рез-та в виде списка
         } else {
             return  bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
         }
     }

//    @Transactional
//    public void setPerson(int personId, int bookId) {
//        Optional<Book> book = bookRepository.findById(bookId);
//        //
//    }

//    @Transactional
//    public void delPerson(int bookId) {
//        //
//    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setBookId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }



}
