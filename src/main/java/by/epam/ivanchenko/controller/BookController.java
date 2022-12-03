package by.epam.ivanchenko.controller;

import by.epam.ivanchenko.model.Book;
import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.service.BookService;
import by.epam.ivanchenko.service.PersonService;
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

    private final BookValidator bookValidator;
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookValidator bookValidator, BookService bookService, PersonService personService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model bookModel, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage) {

        if (page == null || booksPerPage == null) {                                                                     // Если в запросе нет параметров, просто сортируем по году список книг(если пришел параметр).
            bookModel.addAttribute("books", bookService.findAll(sortByYear));
        } else {                                                                                                        // Иначе пагинация: передаем еще сколько страниц и кол-во книг на странице
            bookModel.addAttribute("books",bookService.findWithPagination(page, booksPerPage, sortByYear));
        }
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

        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model bookModel, @ModelAttribute("person") Person person) {
        bookModel.addAttribute("book", bookService.findOne(id));

        Person owner = bookService.getOwner(id);

        if (owner != null) {
            bookModel.addAttribute("owner", bookService.getOwner(id));
        } else {
            bookModel.addAttribute("personList", personService.findAll());
        }

        return "book/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model bookModel) {
        bookModel.addAttribute("book", bookService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book")
                             @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
          // bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(book, id);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/set")
    public String setOwner(@ModelAttribute("person") Person person, @PathVariable("id") int bookId) {
        bookService.setOwner(bookId, person);
        return "redirect:/book/{id}";
    }

    @PatchMapping("/{id}/del")
    public String delOwner(@PathVariable("id") int bookId) {
        bookService.delOwner(bookId);
        return "redirect:/book/{id}";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "book/search";
    }

    @PostMapping("/search")
    public String goSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("book", bookService.getBookStartingWith(query));
        return "book/search";
    }
}

