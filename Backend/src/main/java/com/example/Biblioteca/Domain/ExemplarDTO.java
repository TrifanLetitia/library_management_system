package com.example.Biblioteca.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExemplarDTO {
    private UUID id;
    private Carte carte;
    private String status;
}