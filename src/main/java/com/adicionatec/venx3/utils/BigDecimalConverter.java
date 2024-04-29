package com.adicionatec.venx3.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalConverter {

    // entrada = 1.000,00
    // Saida = 1000.00

    public BigDecimal converter(String valor) {

        if (valor == null) {
            return null;
        }
        valor = valor.replace(".", "").replace(",", ".");

        return new BigDecimal(valor);
    }
}
