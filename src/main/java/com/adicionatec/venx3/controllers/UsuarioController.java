package com.adicionatec.venx3.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adicionatec.venx3.dto.CredenciaisDto;
import com.adicionatec.venx3.dto.TokenDto;
import com.adicionatec.venx3.exceptions.SenhaInvalidaException;
import com.adicionatec.venx3.models.Usuario;
import com.adicionatec.venx3.securityjwt.JwtService;
import com.adicionatec.venx3.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario salve(@RequestBody @Valid Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode( usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		return this.usuarioService.save(usuario);
	}
	
	@PostMapping("/auth")
	public TokenDto autenticar(@RequestBody CredenciaisDto credenciaisDto) {
		
		try {
			Usuario usuario = Usuario
								.builder()
								.username(credenciaisDto.getUsername())
								.senha(credenciaisDto.getSenha())
								.build();
			UserDetails usuarioAutenticado = this.usuarioService.autenticar(usuario);
			
			String token = jwtService.gerarToken( usuario );
				
			return TokenDto
					.builder()
					.username(usuario.getUsername())
					.token(token)
					.build();
			
		} catch (SenhaInvalidaException | UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
		
		
	}
	
}
