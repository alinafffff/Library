package ru.fedorova.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.dto.PatentDTO;
import ru.fedorova.library.model.Book;
import ru.fedorova.library.model.Patent;
import ru.fedorova.library.repository.PatentRepository;
import ru.fedorova.library.service.PatentService;
import ru.fedorova.library.service.mapper.PatentMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatentServiceImpl implements PatentService {
    private static final Logger logger = LoggerFactory.getLogger(PatentServiceImpl.class);
    private final PatentRepository patentRepository;
    private final PatentMapper patentMapper;

    @Override
    public PatentDTO save(PatentDTO patentDTO) {
        logger.info("Saving patent: {}", patentDTO);
        Patent patent = patentMapper.mapDTOToEntity(patentDTO);
        Patent saved = patentRepository.save(patent);
        return patentMapper.mapEntityToDTO(saved);
    }

    @Override
    public PatentDTO findById(Long id) {
        logger.info("Finding patent by id: {}", id);
        Optional<Patent> patent = patentRepository.findById(id);
        return patent.map(patentMapper::mapEntityToDTO)
                .orElseThrow();
    }

    @Override
    public List<PatentDTO> findAll() {
        logger.info("Finding all patents");
        List<Patent> patents = patentRepository.findAll();
        return patents.stream().map(patentMapper::mapEntityToDTO).toList();
    }

    @Override
    public Patent update(Long id, PatentDTO updatedPatent) {
        logger.info("Updating patent with id {}: {}", id, updatedPatent);
        Patent p = patentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patent not found with id: " + id));
        p.setName(updatedPatent.getName());
        p.setAuthor(updatedPatent.getAuthor());
        p.setPatentRegistrationYear(updatedPatent.getPatentRegistrationYear());
        return patentRepository.save(p);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting patent by id: {}", id);
        if (patentRepository.existsById(id)) {
            patentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Patent not found with id: " + id);
        }
    }

    public List<PatentDTO> findPatentsRegisteredAfterYear(LocalDate registrationYear) {
        logger.info("Finding patents registered after year: {}", registrationYear);
        return patentRepository.findPatentsRegisteredAfterYear(registrationYear).stream()
                .map(patentMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

}
