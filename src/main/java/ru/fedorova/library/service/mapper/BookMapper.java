package ru.fedorova.library.service.mapper;

import org.springframework.stereotype.Component;
import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.model.Book;
@Component
public class BookMapper {

    public BookDTO mapEntityToDTO(Book b){

        return BookDTO.builder()
                .id(b.getId())
                .name(b.getName())
                .author(b.getAuthor())
                .isbn(b.getIsbn())
                .releaseYear(b.getReleaseYear())
                .build();
    }

    public Book mapDtoToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setReleaseYear(dto.getReleaseYear());
        return book;
    }
}
