package com.example.Biblioteca.Domain;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ImprumutCreationRequest {
    private UUID id_user;
    private String dataImprumut;
    private String dataRestituire;
    private ExemplarImprumutatRequest exemplarImprumutatRequest;
}
