package by.epam.ivanchenko.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int bookId;
    @Size(min = 1, max = 75, message = "Слишком длинное название книги!")
    @NotEmpty(message = "Название книги не должно быть пустым!")
    private String bookName;

    @Size(min = 1, max = 75, message = "Слишком длинное имя автора!")
    @NotEmpty(message = "Имя автора не должно быть пустым!")
    private String author;


    @Pattern(regexp = "\\d{4}", message = "Введите год в формате XXXX")
    private int bookYear;

    public Book(int bookId, String bookName, String author, int bookYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.bookYear = bookYear;
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
}
