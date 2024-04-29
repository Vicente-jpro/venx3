package com.adicionatec.venx3.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {
    private Integer idCliente;
    private String nome;
    private String bi;
    private String dataCadastro;
}
