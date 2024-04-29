package com.adicionatec.venx3.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adicionatec.venx3.exceptions.SenhaInvalidaException;
import com.adicionatec.venx3.models.Usuario;
import com.adicionatec.venx3.repositories.UsuarioRepository;
import com.adicionatec.venx3.utils.CurrentUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UserDetails autenticar(Usuario usuario) {
		log.info("Autenticando o usuário de nome :{}", usuario.getUsername());
		UserDetails userDetails = this.loadUserByUsername(usuario.getUsername());
		// passwordEncoder.matches(senhaDigitada, senhaGravadaNoBD)
		boolean senhaCorreta = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());

		if (senhaCorreta) {

			log.info("Usuario altenticado com sucesso.");
			return userDetails;
		}

		log.info("Credenciais invalida. Email/Senha não está correta:{}", usuario.getUsername());
		throw new SenhaInvalidaException("Senha invalida");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		String[] usuarioRoles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		UserDetails user = User
				.builder()
				.username(usuario.getUsername())
				.password(usuario.getSenha())
				.roles(usuarioRoles)
				.build();

		return new CurrentUser(user, usuario);
	}


	@Transactional
	public Usuario save(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}

}
