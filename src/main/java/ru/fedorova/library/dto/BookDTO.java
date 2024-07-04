package ru.fedorova.library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private String isbn;
    private LocalDate releaseYear;

}
