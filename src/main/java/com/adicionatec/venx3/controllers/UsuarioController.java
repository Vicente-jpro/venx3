package com.adicionatec.venx3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adicionatec.venx3.models.Usuario;
import com.adicionatec.venx3.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
   // @ApiOperation("Salvar usuário.")
   // @ApiResponse(code = 200, message = "Usuario criado novo usuário")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        return this.usuarioService.save(usuario);
    }

    @GetMapping
    //@ApiOperation("Listar todos usuarios.")
    //@ApiResponse(code = 302, message = "Usuários encontrados.")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Usuario> listarTodos() {
        return this.usuarioService.listarTodos();
    }
}
