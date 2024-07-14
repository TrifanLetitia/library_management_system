package com.example.Biblioteca.Mapper;

import com.example.Biblioteca.Domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImprumutMapper {
    public Imprumut toEntity(ImprumutDTO imprumutDTO) {
        Imprumut imprumut = new Imprumut();
        updateImprumutFromDto(imprumutDTO, imprumut);
        return imprumut;
    }

    public ImprumutDTO toDto(Imprumut imprumut) {
        ImprumutDTO imprumutDTO = new ImprumutDTO();
        imprumutDTO.setId(imprumut.getId());
        imprumutDTO.setData_imprumut(imprumut.getDataImprumut());
        imprumutDTO.setData_restituire(imprumut.getDataResituire());
        imprumutDTO.setUser(imprumut.getUser());
        return imprumutDTO;
    }

    public List<ImprumutDTO> toDtoList(List<Imprumut> imprumutList) {
        return imprumutList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateImprumutFromDto(ImprumutDTO imprumutDTO, Imprumut imprumut) {
      imprumut.setDataImprumut(imprumutDTO.getData_imprumut());
      imprumut.setDataResituire(imprumutDTO.getData_restituire());
      imprumut.setUser(imprumutDTO.getUser());
    }
}
