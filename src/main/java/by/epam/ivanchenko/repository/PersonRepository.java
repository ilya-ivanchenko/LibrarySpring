package by.epam.ivanchenko.repository;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    //List<Book> findByPerson(int id);
    Optional<Person> findByPersonName(String personName);                                            // Для валидатора на проверку существования такого пользователя
}
