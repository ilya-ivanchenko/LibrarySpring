package by.epam.ivanchenko.model;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int bookId;

    private int personId;
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 1, max = 75, message = "Слишком короткое или длинное название книги!")
    private String bookName;

    @NotEmpty(message = "Автор книги не может быть пустым")
    @Size(min = 1, max = 75, message = "Слишком  короткое или длинное имя автора!")
    private String author;

    @Min(value = 1000, message = "Введите корректный год, больше 1500")
    // @Pattern(regexp = "\\d{4}", message = "Введите год в формате XXXX")
    private int bookYear;

    private String personName;

    public Book(int bookId, String bookName, String author, int bookYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.bookYear = bookYear;
    }

//    public Book(int bookId, String bookName, String author, int bookYear, String personName) {
//        this.bookId = bookId;
//        this.bookName = bookName;
//        this.author = author;
//        this.bookYear = bookYear;
//        this.personName = personName;
//    }


    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
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
}
