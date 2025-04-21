package com.api.fleche.repositories;

import com.api.fleche.models.ComandosSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComandosSqlRepository extends JpaRepository<ComandosSql, Long> {

    @Query(value = "SELECT CMD_SQL, CMD_ID, CMD_DESCRICAO, CMD_STATUS FROM TB_CMD WHERE CMD_ID = 1 AND CMD_STATUS = 'A'", nativeQuery = true)
    ComandosSql listaUsuarios();

    @Query(value = "SELECT CMD_SQL, CMD_ID, CMD_DESCRICAO, CMD_STATUS FROM TB_CMD WHERE CMD_ID = 2 AND CMD_STATUS = 'A'", nativeQuery = true)
    ComandosSql buscarDadosUsuario();

    @Query(value = "SELECT CMD_SQL, CMD_ID, CMD_DESCRICAO, CMD_STATUS FROM TB_CMD WHERE CMD_ID = 3 AND CMD_STATUS = 'A'", nativeQuery = true)
    ComandosSql login();

    @Query(value = "SELECT CMD_SQL, CMD_ID, CMD_DESCRICAO, CMD_STATUS FROM TB_CMD WHERE CMD_ID = 4 AND CMD_STATUS = 'A'", nativeQuery = true)
    ComandosSql usuariosOnline();
}
