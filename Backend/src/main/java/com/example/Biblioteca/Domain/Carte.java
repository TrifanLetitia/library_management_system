package com.example.Biblioteca.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "carti")
@NoArgsConstructor
@AllArgsConstructor
public class Carte extends BaseEntity{
    private String title;
    private String author;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String year;
    private String src;
    private Integer no_ex;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "carte", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Exemplar> exemplare = new HashSet<>();

    public Carte(String title, String author, Category category, String year, String src, Integer no_ex) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.src = src;
        this.no_ex = no_ex;
        this.exemplare = new HashSet<>();
    }

    public void addExemplar(Exemplar exemplar) {
        this.exemplare.add(exemplar);
    }

    public void deleteExemplar(Exemplar exemplar) {
        this.exemplare.remove(exemplar);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "id=" + getId() + // presupunând că există o metodă getId() în clasa de bază
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", year='" + year + '\'' +
                ", src='" + src + '\'' +
                ", no_ex=" + no_ex +
                '}';
    }
}
