package com.api.fleche.dao;

import com.api.fleche.dtos.UsuarioDadosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UsuarioDadosDto buscarDadosUsuario(String numero) {
        String sql = "SELECT USU.NOME, USU.GENERO, USU.STATUS, USU.PREFERENCIA, " +
                "USUBAR.STATUS_USUARIO_BAR, BAR.NOME AS NOME_BAR " +
                "FROM TB_USUARIOS USU " +
                "INNER JOIN USUARIO_BAR_SESSAO USUBAR ON USUBAR.USUARIO_ID = USU.ID " +
                "INNER JOIN TB_BARES BAR ON BAR.ID = USUBAR.BAR_ID " +
                "WHERE USU.NUMERO = ?";

        List<UsuarioDadosDto> resultados = jdbcTemplate.query(sql, new Object[]{numero}, (rs, rowNum) ->
                new UsuarioDadosDto(
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
