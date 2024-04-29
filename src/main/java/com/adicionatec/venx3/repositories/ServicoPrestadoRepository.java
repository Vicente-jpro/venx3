package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.models.ServicoPrestado;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

        // data deve estar no formato 20/03/2023
        @Query(value = "SELECT sp.id, sp.cliente_id , c.nome, sp.descricao, sp.valor, "
                        + " sp.data_servico_prestado "
                        + " FROM servico_prestado sp "
                        + " LEFT JOIN cliente c "
                        + " ON c.id = sp.cliente_id"
                        + " where upper( c.nome ) like upper( :nome ) "
                        + " OR sp.data_servico_prestado = DATE(:data)", nativeQuery = true)
        List<ServicoPrestado> findByNomeClienteOrData(
                        @Param("nome") String nome,
                        @Param("data") LocalDate data);

}
