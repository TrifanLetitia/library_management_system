package com.example.Biblioteca.Repository;

import com.example.Biblioteca.Domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExemplarRepository  extends JpaRepository<Exemplar, UUID> {

    Exemplar getExemplarById(UUID id);

    List<Exemplar> findByCarteIdAndStatus(UUID carteId, Status status);
}
