package by.epam.ivanchenko.model;

import jakarta.persistence.*;


import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @Column(name = "name")
    @Pattern(regexp = "[А-ЯA-Z][а-яa-z]+ [А-ЯA-Z][а-яa-z]+ [А-ЯA-Z][а-яa-z]+", message = "Пожалуйста, введите ФИО в формате: Фамилия Имя Отчество")
    private String personName;

    @Column(name = "year")
    @Min(value = 1900, message = "Год рождения не может быть меньше 1900")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person(String personName, int birthYear) {
        this.personName = personName;
        this.birthYear = birthYear;
    }

    public Person() {                                                              // Нужен для Spring
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (personId != person.personId) return false;
        if (birthYear != person.birthYear) return false;
        return Objects.equals(personName, person.personName);
    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + (personName != null ? personName.hashCode() : 0);
        result = 31 * result + birthYear;
        return result;
    }
}
