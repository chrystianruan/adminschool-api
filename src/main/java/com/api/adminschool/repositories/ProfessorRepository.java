package com.api.adminschool.repositories;

import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("SELECT professores FROM Professor professores WHERE professores.id = ?1")
    public Professor findOne(Long id);
}
