package ru.fedorova.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fedorova.library.dto.PatentDTO;
import ru.fedorova.library.model.Patent;
import ru.fedorova.library.service.impl.PatentServiceImpl;
import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/patents")
public class PatentController {

    private final PatentServiceImpl patentService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatentDTO> save(@RequestBody PatentDTO p) {
        PatentDTO saved = patentService.save(p);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }
    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatentDTO> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(patentService.findById(id));
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatentDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(patentService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patent> update(@PathVariable Long id, @RequestBody PatentDTO updatedPatent) {
        Patent saved = patentService.update(id, updatedPatent);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        patentService.deleteById(id);
    }

    @GetMapping(value = "/findPatentsRegisteredAfterYear/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatentDTO>> findBooksWithReleaseYearBetween(@PathVariable LocalDate date) {
        List<PatentDTO> patents = patentService.findPatentsRegisteredAfterYear(date);
        return ResponseEntity.status(HttpStatus.OK)
                .body(patents);
    }
}
