package by.epam.ivanchenko.dao;

import by.epam.ivanchenko.model.Book;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();

        book.setBookId(resultSet.getInt("id"));
        book.setBookName(resultSet.getString("name"));
        book.setAuthor(resultSet.getString("author"));
        book.setBookYear(resultSet.getInt("year"));
        book.setPersonId(resultSet.getInt("person_id"));
        return book;
    }
}
