package com.example.Biblioteca.Repository;

import com.example.Biblioteca.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User getAbonatByCnp(String cnp);

}
