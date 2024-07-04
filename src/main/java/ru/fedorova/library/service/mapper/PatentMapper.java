package ru.fedorova.library.service.mapper;

import org.springframework.stereotype.Component;
import ru.fedorova.library.dto.PatentDTO;
import ru.fedorova.library.model.Book;
import ru.fedorova.library.model.Patent;
@Component
public class PatentMapper {

    public PatentDTO mapEntityToDTO(Patent p){

        return PatentDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .author(p.getAuthor())
                .patentRegistrationYear(p.getPatentRegistrationYear())
                .build();
    }

    public Patent mapDTOToEntity(PatentDTO dto){
        Patent patent = new Patent();
        patent.setId(dto.getId());
        patent.setName(dto.getName());
        patent.setAuthor(dto.getAuthor());
        patent.setPatentRegistrationYear(dto.getPatentRegistrationYear());
        return patent;
    }

}
