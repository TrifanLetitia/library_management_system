package com.example.Biblioteca.Mapper;

import com.example.Biblioteca.Domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExemplarMapper {
    public Exemplar toEntity(ExemplarDTO exemplarDTO) {
        Exemplar exemplar = new Exemplar();
        updateExemplarFromDto(exemplarDTO, exemplar);
        return exemplar;
    }

    public ExemplarDTO toDto(Exemplar exemplar) {
        ExemplarDTO exemplarDTO = new ExemplarDTO();
        exemplarDTO.setId(exemplar.getId());
        exemplarDTO.setStatus(exemplar.getStatus().toString());
        exemplarDTO.setCarte(exemplar.getCarte());
        return exemplarDTO;
    }

    public List<ExemplarDTO> toDtoList(List<Exemplar> exemplare) {
        return exemplare.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateExemplarFromDto(ExemplarDTO exemplarDTO, Exemplar exemplar) {
        exemplar.setStatus(Status.valueOf(exemplarDTO.getStatus()));
        exemplar.setCarte(exemplarDTO.getCarte());
    }
}
