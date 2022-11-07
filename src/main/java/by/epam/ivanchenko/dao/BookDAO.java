package by.epam.ivanchenko.dao;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private static final String SHOW_ALL_BOOKS = "SELECT * FROM book";
    private static final String ADD_BOOK = "INSERT INTO book (name, author, year) VALUES (?, ?, ?)";
    private static final String FIND_BOOK = "SELECT * FROM book WHERE name = ? AND author = ?";
    private static final String FIND_BOOK_ID = "SELECT * FROM book WHERE id = ?";
    private static final String EDIT_BOOK = "UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM book WHERE id = ?";
    private static final String GET_USER_BOOK ="SELECT * FROM person WHERE id = (SELECT person_id FROM book WHERE id = ?)";
//            "SELECT book.*, person.name FROM book JOIN person ON person.id" +
//                    " = book.person_id WHERE book.id = ?;";
private static final String SET_USER_BOOK ="UPDATE book SET person_id = ? WHERE id = ?";
    private static final String DEL_USER_BOOK ="UPDATE book SET person_id = null WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(SHOW_ALL_BOOKS, new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update(ADD_BOOK, book.getBookName(), book.getAuthor(), book.getBookYear());
    }

    public Optional<Book> findBook(String name, String author){
       return jdbcTemplate.query(FIND_BOOK, new BookMapper(), name, author).stream().findAny();
    }

    public Book findBook (int id) {
        return jdbcTemplate.query(FIND_BOOK_ID, new BookMapper(), id).stream().findAny().orElse(null);
    }

    public void updateBook(int id, Book updateBook) {
        jdbcTemplate.update(EDIT_BOOK, updateBook.getBookName(), updateBook.getAuthor(), updateBook.getBookYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK,id);
    }

    public Person getPerson(int bookId) {
        return jdbcTemplate.query(GET_USER_BOOK, new PersonMapper(), bookId).stream().findAny().orElse(null);
    }

    public void setPerson(int personId, int bookId) {
      jdbcTemplate.update(SET_USER_BOOK, personId, bookId);
    }

    public void delPerson(int bookId) {
        jdbcTemplate.update(DEL_USER_BOOK,bookId);
    }

}
