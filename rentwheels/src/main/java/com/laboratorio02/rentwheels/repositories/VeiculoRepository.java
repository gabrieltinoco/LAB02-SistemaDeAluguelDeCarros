package com.laboratorio02.rentwheels.repositories;

import com.laboratorio02.rentwheels.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
