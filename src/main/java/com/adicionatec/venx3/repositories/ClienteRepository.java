package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
