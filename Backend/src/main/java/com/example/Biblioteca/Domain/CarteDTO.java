package com.example.Biblioteca.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteDTO {

    private UUID id;
    private String title;
    private String author;
    private String category;
    private String year;
    private String src;
    private Integer no_ex;
    private Set<Exemplar> exemplare = new HashSet<>();

    public CarteDTO(String title, String author, String category, String year, String src, Integer no_ex) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.src = src;
        this.no_ex = no_ex;
        this.exemplare = new HashSet<>();
    }

}