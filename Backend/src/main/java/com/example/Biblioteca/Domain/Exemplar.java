package com.example.Biblioteca.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.UUID;

@Setter
@Getter
@Entity
@Data
@Table(name = "exemplare")
@NoArgsConstructor
@AllArgsConstructor
public class Exemplar extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_carte", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Carte carte;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "Exemplar{" +
                "id=" + getId() +
                ", status=" + status +
                ", carteId=" + (carte != null ? carte.getId() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exemplar)) return false;

        Exemplar exemplar = (Exemplar) o;

        return getId() != null ? getId().equals(exemplar.getId()) : exemplar.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
