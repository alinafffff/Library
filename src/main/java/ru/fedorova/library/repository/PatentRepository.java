package ru.fedorova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fedorova.library.model.Patent;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatentRepository extends JpaRepository<Patent,Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM Patent WHERE patent_registration_year > :registrationYear
    """)
    List<Patent> findPatentsRegisteredAfterYear(@Param("registrationYear") LocalDate registrationYear);

}
