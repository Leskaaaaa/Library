package ru.leska.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leska.com.model.Book;
import ru.leska.com.model.Person;
import ru.leska.com.repositories.BookRepository;
import ru.leska.com.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book bookUpdate, int id) {
        bookUpdate.setId(id);
        bookRepository.save(bookUpdate);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> findPersonByBookId(int id) {
        return personRepository.findPersonByBooks_Id(id);
    }

    @Transactional
    public void deletePersonByBookId(int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setPerson(null);
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book not found");
        }
    }

    @Transactional
    public void addPersonForBook(Person person, int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            book.get().setPerson(person);
            bookRepository.save(book.get());
        } else {
            throw new NullPointerException("Book not found");
        }
    }
}
