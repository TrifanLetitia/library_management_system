package com.example.Biblioteca.Domain;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "exemplar_imprumutat")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ExemplarImprumutat extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imprumut")
    private Imprumut imprumut;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_exemplar")
    private Exemplar exemplar;
}
