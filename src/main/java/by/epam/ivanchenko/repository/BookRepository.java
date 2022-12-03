package by.epam.ivanchenko.repository;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  BookRepository extends JpaRepository<Book, Integer> {

    //Optional<Person> findByBookId(int id);

    //void findByPersonId(int personId);

    List<Book> findByBookNameStartingWith(String bookName);

    Optional<Book> findByBookNameAndAuthor(String name, String author);
}
