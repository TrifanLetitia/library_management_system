package com.example.Biblioteca.Repository;

import com.example.Biblioteca.Domain.Imprumut;
import com.example.Biblioteca.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImprumutRepository extends JpaRepository<Imprumut, UUID> {

    Imprumut getImprumutById(UUID id);

}
