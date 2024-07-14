package com.example.Biblioteca.Repository;

import com.example.Biblioteca.Domain.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarteRepository extends JpaRepository<Carte, UUID> {

    Carte getCarteById(UUID id);

}