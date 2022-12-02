package by.epam.ivanchenko.service;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson, int id) {
        updatedPerson.setPersonId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> findPersonBook(int id) {                                                      // Вывод списка книг человека, которые он взял
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {

            Hibernate.initialize(person.get().getBooks());                                          // Для получения  книг человека
/* Без initialize  возможна ошибка ленивой инициализации за пределами транзакции. Но дальше мы итерируемся по сущностям(книгам)
* и эти сущности будут загружены, в таком случае блок initialize  можно не использовать.
*/
            person.get().getBooks().forEach(book -> {                                               // Проверяем книгу на просрочку (просрочка - это 10 суток)
                long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());   // Проверяем есть ли просорочка более 10 суток (864000000 милисек.)
                if (diffInMillis > 864000000) {
                    book.setExpired(true);
                }
            });

            return person.get().getBooks();
        } else {
            return Collections.emptyList();                                                         // Если человека нет, то возвращаем пустой список
        }
    }

    public Optional<Person> getByPersonName(String personName) {
        return personRepository.findByPersonName(personName);
    }

}
