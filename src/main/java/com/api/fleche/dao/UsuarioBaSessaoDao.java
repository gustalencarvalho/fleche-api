package com.api.fleche.dao;

import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.dtos.UsuarioDto;
import com.api.fleche.repositories.ComandosSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioBaSessaoDao {

    private final JdbcTemplate jdbcTemplate;
    private final ComandosSqlRepository comandosSqlRepository;

    public List<UsuarioBarDto> usuariosParaListar(String qrCode, Long usuarioId) {
        String sql = comandosSqlRepository.listaUsuarios().getCmdSql();

        List<UsuarioBarDto> resultados = jdbcTemplate.query(sql, new Object[]{qrCode, usuarioId}, (rs, rowNum) ->
                new UsuarioBarDto(
                        rs.getString("NOME"),
                        rs.getString("GENERO"),
                        rs.getInt("IDADE")
                )
        );

        return resultados.isEmpty() ? Collections.emptyList() : resultados;
    }

}
