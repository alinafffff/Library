package ru.fedorova.library.service;

import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.model.Book;

import java.util.List;

public interface BookService {
    BookDTO save(BookDTO b);

    BookDTO findById(Long id);

    List<BookDTO> findAll();

    Book update(Long id, BookDTO updatedBook);

    void deleteById(Long id);
}
