package by.epam.ivanchenko.controller;

import by.epam.ivanchenko.dao.BookDAO;
import by.epam.ivanchenko.dao.PersonDAO;
import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;

    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model bookModel) {
        bookModel.addAttribute("books", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookDAO.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model bookModel, @ModelAttribute("person") Person person) {
        bookModel.addAttribute("book", bookDAO.findBook(id));
        bookModel.addAttribute("owner", bookDAO.getPerson(id));
        bookModel.addAttribute("personList",personDAO.index());
        return "book/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model bookModel) {
        bookModel.addAttribute("book", bookDAO.findBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book")
                             @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/set")
    public String setBook(@ModelAttribute("person") Person person, @PathVariable("id") int bookId) {
        bookDAO.setPerson(person.getPersonId(), bookId);
        return "redirect:/book/{id}";
    }

    @PatchMapping("/{id}/del")
    public String delBook( @PathVariable("id") int bookId) {
        bookDAO.delPerson(bookId);
        return "redirect:/book/{id}";
    }

}
