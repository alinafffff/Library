package ru.fedorova.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.model.Book;
import ru.fedorova.library.service.impl.BookServiceImpl;
import ru.fedorova.library.service.impl.ExcelReportServiceImpl;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookServiceImpl bookService;
    private final ExcelReportServiceImpl excelReportService;
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> save(@RequestBody BookDTO bookDTO) {
        BookDTO saved = bookService.save(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }
    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findById(id));
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookDTO updatedBook) {
        Book savedBook = bookService.update(id, updatedBook);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping(value = "/findByReleaseYearBetween/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBooksWithReleaseYearBetween(@PathVariable LocalDate startDate,
                                                                         @PathVariable LocalDate endDate) {
        List<BookDTO> books = bookService.findBooksWithReleaseYearBetween(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(books);
    }

    @GetMapping(value = "/findByAuthorSortedByYear/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBooksByAuthorSortedByYear(@PathVariable String author) {
        List<BookDTO> books = bookService.findBooksByAuthorSortedByYear(author);
        return ResponseEntity.status(HttpStatus.OK)
                .body(books);
    }

    @GetMapping(value = "/findByIsbnContaining/{substring}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBooksByIsbnContaining(@PathVariable String substring) {
        List<BookDTO> books = bookService.findBooksByIsbnContaining(substring);
        return ResponseEntity.status(HttpStatus.OK)
                .body(books);
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() {
        List<BookDTO> books = bookService.findAll();
        ByteArrayInputStream in = excelReportService.generateExcelReport(books);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=books_report.xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

}
