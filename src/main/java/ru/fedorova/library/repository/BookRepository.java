package ru.fedorova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fedorova.library.model.Book;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(nativeQuery = true, value = """
         SELECT * FROM Book WHERE release_year BETWEEN :startDate AND :endDate
                    """)
    List<Book> findBooksWithReleaseYearBetween(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query(nativeQuery = true, value = """
        SELECT * FROM Book WHERE author = :author ORDER BY release_year
        """)
    List<Book> findBooksByAuthorSortedByYear(@Param("author") String author);

    @Query(nativeQuery = true, value = """
        SELECT * FROM Book WHERE isbn LIKE %:substring%
        """)
    List<Book> findBooksByIsbnContaining(@Param("substring") String substring);


}
