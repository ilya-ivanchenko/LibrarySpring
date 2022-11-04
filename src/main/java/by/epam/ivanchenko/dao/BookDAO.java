package by.epam.ivanchenko.dao;

import by.epam.ivanchenko.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private static final String SHOW_ALL_BOOKS = "SELECT * FROM book";
    private static final String ADD_BOOK = "INSERT INTO book (name, author, year) VALUES (?, ?, ?)";

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


}
