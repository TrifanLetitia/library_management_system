package com.example.Biblioteca.Mapper;

import com.example.Biblioteca.Domain.User;
import com.example.Biblioteca.Domain.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AbonatMapper {
    public User toEntity(UserDTO abonatDTO) {
        User abonat = new User();
        updateAbonatFromDto(abonatDTO, abonat);
        return abonat;
    }

    public UserDTO toDto(User abonat) {
        UserDTO abonatDTO = new UserDTO();
        abonatDTO.setId(abonat.getId());
        abonatDTO.setCnp(abonat.getCnp());
        abonatDTO.setPassword(abonat.getPassword());
        abonatDTO.setName(abonat.getName());
        abonatDTO.setAdress(abonat.getAdress());
        abonatDTO.setPhone(abonat.getPhone());
        return abonatDTO;
    }

    public List<UserDTO> toDtoList(List<User> abonati) {
        return abonati.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateAbonatFromDto(UserDTO abonatDTO, User abonat) {
        abonat.setName(abonatDTO.getName());
        abonat.setAdress(abonatDTO.getAdress());
        abonat.setPhone(abonatDTO.getPhone());
        abonat.setCnp(abonatDTO.getCnp());
        abonat.setPassword(abonatDTO.getPassword());
    }
}

