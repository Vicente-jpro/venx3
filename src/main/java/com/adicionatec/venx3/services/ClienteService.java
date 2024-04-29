package com.adicionatec.venx3.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repositories.ClienteRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		log.info("ClienteService - salvando cliente. ");
		cliente.setDataCadastro(LocalDate.now());
		return this.clienteRepository.save(cliente);
	}

	public Cliente getCliente(Integer idCliente) {
		log.info("ClienteService - buscando o cliente com id: " + idCliente);
		return this.clienteRepository
				.findById(idCliente)
				.orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado. Id invalido :" + idCliente));
	}

	public void eliminar(Integer idCliente) {
		log.info("ClienteService - eliminar cliente.");
		Cliente cliente = this.getCliente(idCliente);
		this.clienteRepository.delete(cliente);
	}

	public List<Cliente> listarClientes() {
		log.info("ClienteService - listando os clientes.");
		return this.clienteRepository.findAll();
	}
}
