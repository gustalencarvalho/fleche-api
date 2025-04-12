package com.api.fleche.dao;

import com.api.fleche.dtos.UsuarioDadosDto;
import com.api.fleche.repositories.ComandosSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioDao {

    private final JdbcTemplate jdbcTemplate;
    private final ComandosSqlRepository comandosSqlRepository;

    public UsuarioDadosDto buscarDadosUsuario(String numero) {
        String sql = comandosSqlRepository.buscarDadosUsuario().getCmdSql();

        List<UsuarioDadosDto> resultados = jdbcTemplate.query(sql, new Object[]{numero}, (rs, rowNum) ->
                new UsuarioDadosDto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("GENERO"),
                        rs.getString("STATUS"),
                        rs.getString("PREFERENCIA"),
                        rs.getString("STATUS_USUARIO_BAR"),
                        rs.getString("NOME_BAR")
                )
        );

        return resultados.isEmpty() ? null : resultados.get(0);
    }

}
