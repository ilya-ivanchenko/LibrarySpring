package by.epam.ivanchenko.dao;

import by.epam.ivanchenko.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
       Person person = new Person();

        person.setPersonId(resultSet.getInt("id"));
        person.setPersonName(resultSet.getString("name"));
        person.setBirthYear(resultSet.getInt("year"));
        return person;
    }
}
