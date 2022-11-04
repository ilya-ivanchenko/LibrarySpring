package by.epam.ivanchenko.controller;

import by.epam.ivanchenko.dao.BookDAO;
import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
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
//        bookValidator.validate(book, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "book/new";
//        }
        bookDAO.save(book);
        return "redirect:/book";
  }

}
