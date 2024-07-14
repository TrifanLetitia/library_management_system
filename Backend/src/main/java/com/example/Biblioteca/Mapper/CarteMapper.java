package com.example.Biblioteca.Mapper;

import com.example.Biblioteca.Domain.Carte;
import com.example.Biblioteca.Domain.CarteDTO;
import com.example.Biblioteca.Domain.Category;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarteMapper {
    public Carte toEntity(CarteDTO carteDTO) {
        Carte carte = new Carte();
        updateCarteFromDto(carteDTO, carte);
        return carte;
    }

    public CarteDTO toDto(Carte carte) {
        CarteDTO carteDTO = new CarteDTO();
        carteDTO.setId(carte.getId());
        carteDTO.setTitle(carte.getTitle());
        carteDTO.setAuthor(carte.getAuthor());
        carteDTO.setYear(carte.getYear());
        carteDTO.setCategory(carte.getCategory().toString());
        carteDTO.setSrc(carte.getSrc());
        carteDTO.setNo_ex(carte.getNo_ex());
        if (carteDTO.getExemplare() != null) {
            carteDTO.getExemplare().clear();
            if (carte.getExemplare() != null) {
                carteDTO.getExemplare().addAll(carte.getExemplare());
            }
        } else {
            carteDTO.setExemplare(new HashSet<>(carte.getExemplare()));
        }
        return carteDTO;
    }

    public List<CarteDTO> toDtoList(List<Carte> carti) {
        return carti.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateCarteFromDto(CarteDTO carteDTO, Carte carte) {
        carte.setTitle(carteDTO.getTitle());
        carte.setAuthor(carteDTO.getAuthor());
        carte.setYear(carteDTO.getYear());
        carte.setCategory(Category.valueOf(carteDTO.getCategory()));
        carte.setSrc(carteDTO.getSrc());
        carte.setNo_ex(carteDTO.getNo_ex());
        if (carte.getExemplare() != null) {
            carte.getExemplare().clear();
            if (carteDTO.getExemplare() != null) {
                carte.getExemplare().addAll(carteDTO.getExemplare());
            }
        } else {
            carte.setExemplare(new HashSet<>(carteDTO.getExemplare()));
        }
    }
}
