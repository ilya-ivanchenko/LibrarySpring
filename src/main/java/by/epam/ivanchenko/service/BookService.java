package by.epam.ivanchenko.service;


import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public List<Book> getBookStartingWith(String query) {
        return bookRepository.findByBookNameStartingWith(query);
    }

    public Person getOwner(int id) {                                                                       // Hibernate.initialize() не нужен, т.к. owner (Person) загружается не лениво (сторона связи One)
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public Optional<Book> getByNameAndAuthor(String name, String author) {                                  // Для валидатора
        return bookRepository.findByBookNameAndAuthor(name, author);
    }

    @Transactional
    public void setOwner(int id, Person person) {                                                           // Книга выдается человеку, устанавливаем владельца и время выдачи
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTakenAt(new Date());
                });
    }

    @Transactional
    public void delOwner(int id) {                                                                          // Человек возвращает книгу, в книге владельца и дату выдачи убираем
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        Book bookTobeUpdated = bookRepository.findById(id).get();                                           // Добавляем книгу, кот. не нах-ся в Persistence context, поэтому далее save()
        updatedBook.setBookId(id);
        updatedBook.setOwner(bookTobeUpdated.getOwner());                                                   // Чтобы не потерять владельца, может быть null

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }


}
