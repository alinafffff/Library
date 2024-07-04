package ru.fedorova.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.model.Book;
import ru.fedorova.library.repository.BookRepository;
import ru.fedorova.library.service.BookService;
import ru.fedorova.library.service.mapper.BookMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDTO save(BookDTO bookDTO) {
        logger.info("Saving book: {}", bookDTO);
        Book book = bookMapper.mapDtoToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapEntityToDTO(savedBook);
    }

    @Override
    public BookDTO findById(Long id) {
        logger.info("Finding book by id: {}", id);
        return bookRepository.findById(id)
                .map(bookMapper::mapEntityToDTO)
                .orElseThrow();
    }

    @Override
    public List<BookDTO> findAll() {
        logger.info("Finding all books");
        return bookRepository.findAll().stream()
                .map(bookMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Book update(Long id, BookDTO updatedBook) {
        logger.info("Updating book with id {}: {}", id, updatedBook);
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        b.setName(updatedBook.getName());
        b.setAuthor(updatedBook.getAuthor());
        b.setIsbn(updatedBook.getIsbn());
        b.setReleaseYear(updatedBook.getReleaseYear());
        return bookRepository.save(b);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting book by id: {}", id);
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    public List<BookDTO> findBooksWithReleaseYearBetween(LocalDate startDate, LocalDate endDate) {
        logger.info("Finding books with release year between {} and {}", startDate, endDate);
        return bookRepository.findBooksWithReleaseYearBetween(startDate, endDate).stream()
                .map(bookMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findBooksByAuthorSortedByYear(String author) {
        logger.info("Finding books by author: {}", author);
        return bookRepository.findBooksByAuthorSortedByYear(author).stream()
                .map(bookMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findBooksByIsbnContaining(String substring) {
        logger.info("Finding books with ISBN containing: {}", substring);
        return bookRepository.findBooksByIsbnContaining(substring).stream()
                .map(bookMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

}
