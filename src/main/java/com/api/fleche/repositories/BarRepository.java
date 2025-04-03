package com.api.fleche.repositories;

import com.api.fleche.models.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarRepository extends JpaRepository<Bar, Long> {
    boolean existsByCnpj(String cnpj);
}
