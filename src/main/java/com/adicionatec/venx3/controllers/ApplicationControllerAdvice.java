package com.adicionatec.venx3.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.adicionatec.vexceptions.ClienteNotFoundException;
import com.example.exceptions.DadosInvalidoException;
import com.example.exceptions.ServicoPrestadoNotFoundException;
import com.example.exceptions.UsuarioCadastradoException;
import com.example.exceptions.UsuarioNotFoundException;
import com.example.utils.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	private String mensagemErro;

	@ResponseBody
	@ExceptionHandler(ClienteNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors clienteNotFoundExceptionHandle(ClienteNotFoundException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(UsuarioCadastradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors usuarioCadastradoExceptionHandle(UsuarioCadastradoException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(DadosInvalidoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors dadosInvalidoExceptionHandle(DadosInvalidoException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(ServicoPrestadoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors servicoPrestadoNotFoundExceptionHandle(
			ServicoPrestadoNotFoundException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(UsuarioNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors usuarioNotFoundExceptionHandle(
			UsuarioNotFoundException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity ResponseStatusExceptionHandle(ResponseStatusException ex) {
		String messagemErro = ex.getReason();
		HttpStatus codigoStatus = ex.getStatus();
		ApiErrors apiErrors = new ApiErrors(messagemErro);
		return new ResponseEntity(apiErrors, codigoStatus);

	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors validateFieldsHandle(MethodArgumentNotValidException ex) {
		List<String> erros = ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());

		return new ApiErrors(erros);
	}
}
