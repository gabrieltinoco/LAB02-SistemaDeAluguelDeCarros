package com.laboratorio02.rentwheels.repositories;

import com.laboratorio02.rentwheels.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}