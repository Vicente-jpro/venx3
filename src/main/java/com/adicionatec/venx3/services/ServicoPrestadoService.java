package com.adicionatec.venx3.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.converter.ServicoPrestadoConverter;
import com.example.dto.ServicoPrestadoDto;
import com.example.exceptions.DadosInvalidoException;
import com.example.models.Cliente;
import com.example.models.ServicoPrestado;
import com.example.repositories.ServicoPrestadoRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ServicoPrestadoService {

    @Autowired
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicoPrestadoConverter servicoPrestadoConverter;

    @Transactional
    public ServicoPrestadoDto salvar(ServicoPrestadoDto servicoPrestadoDto) {

        try {

            log.info("ServicoPrestadoService - Salando a prestação de servico com id_cliente: "
                    + servicoPrestadoDto.getCliente());
            Cliente cliente = this.clienteService.getCliente(servicoPrestadoDto.getCliente().getIdCliente());

            ServicoPrestado servicoPrestado = new ServicoPrestado();
            servicoPrestado.setCliente(cliente);
            servicoPrestado.setDescricao(servicoPrestadoDto.getDescricao());
            servicoPrestado.setValor(servicoPrestadoDto.getValor());

            LocalDate data = LocalDate.parse(servicoPrestadoDto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            servicoPrestado.setData(data);

            this.servicoPrestadoRepository.save(servicoPrestado);

            ServicoPrestadoDto dto = servicoPrestadoConverter.converterServicoPrestado(servicoPrestado);

            return dto;
        } catch (DateTimeParseException e) {
            log.error("ServicoPrestadoService - ERRO ao salvar prestação de servico. \n formato da data invalido: ");

            throw new DadosInvalidoException("Formato da data inválido. Usar o formato dd/MM/yyyy.");
        }

    }

    public List<ServicoPrestadoDto> findByNomeClienteOrData(String nome, LocalDate data) {
        List<ServicoPrestado> servicoPrestados = this.servicoPrestadoRepository
                .findByNomeClienteOrData(nome, data);

        List<ServicoPrestadoDto> lista = new ArrayList<>();

        for (ServicoPrestado servico : servicoPrestados) {
            ServicoPrestadoDto sp = this.servicoPrestadoConverter
                    .converterServicoPrestado(servico);
            lista.add(sp);
        }

        return lista;
    }

    public List<ServicoPrestadoDto> listarTodos() {
        List<ServicoPrestado> servicoPrestados = this.servicoPrestadoRepository
                .findAll();

        List<ServicoPrestadoDto> lista = new ArrayList<>();

        for (ServicoPrestado servico : servicoPrestados) {
            ServicoPrestadoDto sp = this.servicoPrestadoConverter
                    .converterServicoPrestado(servico);
            lista.add(sp);
        }

        return lista;
    }

}
