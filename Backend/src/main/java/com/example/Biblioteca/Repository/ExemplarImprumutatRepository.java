package com.example.Biblioteca.Repository;

import com.example.Biblioteca.Domain.ExemplarImprumutat;
import com.example.Biblioteca.Domain.Imprumut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExemplarImprumutatRepository extends JpaRepository<ExemplarImprumutat, UUID> {
    ExemplarImprumutat getExemplarImprumutatById(UUID id);
}
