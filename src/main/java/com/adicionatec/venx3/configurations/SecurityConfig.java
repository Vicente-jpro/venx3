package com.adicionatec.venx3.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adicionatec.venx3.securityjwt.JwtAuthFilter;
import com.adicionatec.venx3.securityjwt.JwtService;
import com.adicionatec.venx3.services.UsuarioService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired 
	private JwtService jwtService;
	
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService)
				.passwordEncoder( passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // Para trabalhar no modo API
			.authorizeHttpRequests() // Autorizar o pedido
			// .antMatchers("/api/clientes/**").authenticated() Cliente deve estar autenticado para acessar API clientes
			//.antMatchers("/api/clientes/**").hasRole("USER") Cliente deve ter ROLE "USER" para acessar API
			// .antMatchers("/api/clientes/**").hasAuthority("MANTER USUARIO") Autenticado permante para acessar API
			.antMatchers("/api/items/**").permitAll() // Permitir todos os usuários acessarem essa rota
			.antMatchers("/api/clientes/**")
				.hasAnyRole("USER", "ADMIN")
				
			.antMatchers("/api/pedidos/**")
				.hasAnyRole("USER", "ADMIN")
			
			.antMatchers("/api/produtos/**")
				.hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/usuarios/**")
				.permitAll()
			.anyRequest().authenticated()
			.and()
			// .formLogin() Fazer autenticacao via formulario html
		// .httpBasic(); Utilizado para fazer autenticação via header no ato da requisição.
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	// Permitir o uso do swagger sem ser autenticado 
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers(
				 "/swagger-resources/**",
			        "/swagger-ui.html",
			        "/v2/api-docs",
			        "/webjars/**"
				
				);
		
	}
	
}
