package by.epam.ivanchenko.repository;

import by.epam.ivanchenko.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByPersonName(String personName);                                            // Для валидатора на проверку существования такого пользователя
}
