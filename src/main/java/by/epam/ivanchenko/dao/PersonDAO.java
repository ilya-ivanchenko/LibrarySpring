package by.epam.ivanchenko.dao;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private static final String SHOW_ALL_PERSON = "SELECT * FROM person";
    private static final String ADD_PERSON = "INSERT INTO person (name, year) VALUES(?, ?)";
    private static final String FIND_PERSON = "SELECT * FROM person WHERE name = ?";
    private static final String FIND_PERSON_ID = "SELECT * FROM person WHERE id = ?";
    private static final String EDIT_PERSON = "UPDATE person SET name = ?, year = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE  id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(SHOW_ALL_PERSON, new PersonMapper());
    }

    public Optional<Person> show(String personName) {
        return jdbcTemplate.query(FIND_PERSON, new BeanPropertyRowMapper<>(Person.class), personName).stream().findAny();
    }

    public Person find(int id) {
        return jdbcTemplate.query(FIND_PERSON_ID, new PersonMapper(), id).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update(ADD_PERSON, person.getPersonName(), person.getBirthYear());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update(EDIT_PERSON, updatePerson.getPersonName(), updatePerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_PERSON, id);
    }

    public List<Book> getPersonBook(int id) {

    }
}
