package com.example.Biblioteca.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ExemplarImprumutatRequest {
    private UUID id_imprumut;
    private List<UUID> id_carti;
}
