package ru.fedorova.library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatentDTO {

    private Long id;
    private String name;
    private String author;
    private LocalDate patentRegistrationYear;

}
