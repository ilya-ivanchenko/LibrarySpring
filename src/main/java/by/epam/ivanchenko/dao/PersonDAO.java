package by.epam.ivanchenko.dao;

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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SHOW_ALL_PERSON = "SELECT * FROM person";
    private static final String ADD_PERSON = "INSERT INTO person (name, year) VALUES(?, ?)";
    private static final String FIND_PERSON_YEAR = "SELECT * FROM person WHERE year = ?";
    private static final String FIND_PERSON_NAME = "SELECT * FROM person WHERE name = ?";
    private static final String FIND_PERSON_ID = "SELECT * FROM person WHERE id = ?";
    private static final String EDIT_PERSON = "UPDATE person SET name = ?, year = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE  id = ?";


    public List<Person> index() {
        return jdbcTemplate.query(SHOW_ALL_PERSON, new PersonMapper());
    }

    public Optional<Person> show(String personName) {
        return jdbcTemplate.query(FIND_PERSON_NAME, new BeanPropertyRowMapper<>(Person.class),personName).stream().findAny();
    }

    public Optional<Person> show(int birthYear) {
        return jdbcTemplate.query(FIND_PERSON_YEAR, new BeanPropertyRowMapper<>(Person.class),birthYear).stream().findAny();
    }

//    public Optional<Person> find(int id) {
//        return jdbcTemplate.query(FIND_PERSON_ID, new PersonMapper(),id).stream().findAny();
//    }

    public void save(Person person) {
        jdbcTemplate.update(ADD_PERSON, person.getPersonName(), person.getBirthYear());
    }
//
//    public void update(int id, Person updatePerson) {
//        jdbcTemplate.update(EDIT_PERSON, updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getAddress(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update(DELETE_PERSON, id);
//    }
//
//
//
//
//
//
//
//    // тест пакетной вставки 1000 записей
//
//
//    public void testMultipleUpdate() {
//        List<Person> people = create1000People();
//
//        long before = System.currentTimeMillis();
//
//        for (Person person : people) {
//            jdbcTemplate.update(ADD_PERSON_TEST, person.getId(), person.getName(), person.getAge(), person.getEmail());
//        }
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Time: " + (after - before));               // время вставки 1000 записей
//    }
//
//    public void testBatchUpdate() {                                   // пакетная вставка 1000 записей
//        List<Person> people = create1000People();
//
//        long before = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate(ADD_PERSON_TEST, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                preparedStatement.setInt(1, people.get(i).getId());
//                preparedStatement.setString(2, people.get(i).getName());
//                preparedStatement.setInt(3, people.get(i).getAge());
//                preparedStatement.setString(4, people.get(i).getEmail());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return people.size();
//            }
//        });
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Time with batch : " + (after - before));
//    }
//
//    public List<Person> create1000People() {
//        List<Person> people = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++) {
//            people.add(new Person(i, "Name" + i, 30, "test" + i + "@gmail.com", "my address"));
//        }
//        return people;
//    }
}
