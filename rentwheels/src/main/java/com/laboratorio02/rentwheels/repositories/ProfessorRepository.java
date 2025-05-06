package com.laboratorio02.rentwheels.repositories;

import com.laboratorio02.rentwheels.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}


