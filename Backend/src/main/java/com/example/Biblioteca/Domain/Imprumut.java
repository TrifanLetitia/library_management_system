package com.example.Biblioteca.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "imprumuturi")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Imprumut extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    private String dataImprumut;
    private String dataResituire;
}