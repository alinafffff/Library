package ru.fedorova.library.service;

import ru.fedorova.library.dto.PatentDTO;
import ru.fedorova.library.model.Patent;

import java.util.List;
import java.util.Optional;

public interface PatentService {
    PatentDTO save(PatentDTO p);

    PatentDTO findById(Long id);

    List<PatentDTO> findAll();

    Patent update(Long id, PatentDTO updatedPatent);

    void deleteById(Long id);
}
