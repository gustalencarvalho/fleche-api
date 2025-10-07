package com.api.fleche.dao;

import com.api.fleche.model.dtos.BarsDto;
import com.api.fleche.model.dtos.UserBarDto;
import com.api.fleche.repository.CommandSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBarSessionDao {

    private final JdbcTemplate jdbcTemplate;
    private final CommandSqlRepository commandSqlRepository;

    public Page<UserBarDto> usuariosParaListar(String qrCode, Long usuarioId, Pageable pageable) {
        String sql = commandSqlRepository.allUsers().getCmdSql();
        List<UserBarDto> resultados = jdbcTemplate.query(sql, new Object[]{qrCode, usuarioId, usuarioId}, (rs, rowNum) ->
                new UserBarDto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("GENERO"),
                        rs.getInt("IDADE")
                )
        );

        if (resultados.isEmpty()) {
            return Page.empty(pageable);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultados.size());
        List<UserBarDto> usuariosPaginados = resultados.subList(start, end);
        return new PageImpl<>(usuariosPaginados, pageable, resultados.size());
    }

    public List<BarsDto> listarTotalUsuariosPorBar(Long usuarioId) {
        String sql = commandSqlRepository.usersOnline().getCmdSql();

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BarsDto dto = new BarsDto();
            dto.setId(rs.getLong("BAR_ID"));
            dto.setName(rs.getString("NOME"));
            dto.setAddress(rs.getString("ENDERECO"));
            dto.setDistrict(rs.getString("BAIRRO"));
            dto.setCity(rs.getString("CIDADE"));
            dto.setPhone(rs.getInt("NUMERO"));
            dto.setQrCode(rs.getString("QR_CODE"));
            dto.setUsersOnline(rs.getLong("TOTAL_USUARIOS"));
            return dto;
        }, usuarioId);
    }

}
