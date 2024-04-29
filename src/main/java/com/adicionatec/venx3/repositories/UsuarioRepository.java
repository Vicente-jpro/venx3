package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);

    boolean existsByEmail(String email);

}
