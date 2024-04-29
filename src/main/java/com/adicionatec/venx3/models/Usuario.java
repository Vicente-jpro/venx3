package com.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer idUsuario;

    @Column(name = "username")
    @NotBlank(message = "Campo user name não pode estar vazio.")
    private String username;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Campo email não pode estar vazio.")
    private String email;

    @Column(name = "passwrd")
    @NotBlank(message = "Campo palavra pass não pode estar vazio.")
    private String passwrd;

}
