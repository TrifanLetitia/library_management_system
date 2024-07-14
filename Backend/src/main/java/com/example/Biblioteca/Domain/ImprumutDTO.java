package com.example.Biblioteca.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImprumutDTO {
    UUID id;
    private User user;
    private String data_imprumut;
    private String data_restituire;
}
