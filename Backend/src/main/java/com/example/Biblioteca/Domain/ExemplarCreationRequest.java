package com.example.Biblioteca.Domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExemplarCreationRequest {
    private String id_carte;
    private String status;
}