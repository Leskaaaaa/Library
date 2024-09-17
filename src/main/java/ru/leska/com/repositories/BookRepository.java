package ru.leska.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leska.com.model.Book;
import ru.leska.com.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByPersonId(Integer id);

    void deletePersonById(Integer id);
}
