package com.api.fleche.repository;

import com.api.fleche.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarRepository extends JpaRepository<Bar, Long> {
    boolean existsByCnpj(String cnpj);
    Optional<Bar> findByQrCode(String qrCode);
}
