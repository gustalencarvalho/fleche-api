package com.api.fleche.dao;

import com.api.fleche.dtos.BaresDto;
import com.api.fleche.dtos.UsuarioBarDto;
import com.api.fleche.repositories.ComandosSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioBarSessaoDao {

    private final JdbcTemplate jdbcTemplate;
    private final ComandosSqlRepository comandosSqlRepository;

    public Page<UsuarioBarDto> usuariosParaListar(String qrCode, Long usuarioId, Pageable pageable) {
        String sql = comandosSqlRepository.listaUsuarios().getCmdSql();

        List<UsuarioBarDto> resultados = jdbcTemplate.query(sql, new Object[]{qrCode, usuarioId}, (rs, rowNum) ->
                new UsuarioBarDto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getInt("IDADE")
                )
        );

        if (resultados.isEmpty()) {
            return Page.empty(pageable);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultados.size());

        List<UsuarioBarDto> usuariosPaginados = resultados.subList(start, end);

        return new PageImpl<>(usuariosPaginados, pageable, resultados.size());
    }

    public List<BaresDto> listarTotalUsuariosPorBar() {
        String sql = comandosSqlRepository.usuariosOnline().getCmdSql();

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BaresDto dto = new BaresDto();
            dto.setId(rs.getLong("BAR_ID"));
            dto.setNome(rs.getString("NOME"));
            dto.setEndereco(rs.getString("ENDERECO"));
            dto.setBairro(rs.getString("BAIRRO"));
            dto.setCidade(rs.getString("CIDADE"));
            dto.setNumero(rs.getInt("NUMERO"));
            dto.setQrCode(rs.getString("QR_CODE"));
            dto.setUsuariosOnline(rs.getLong("TOTAL_USUARIOS"));
            return dto;
        });
    }


}
