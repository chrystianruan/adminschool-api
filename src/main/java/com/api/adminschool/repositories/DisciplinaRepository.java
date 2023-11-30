package com.api.adminschool.repositories;

import com.api.adminschool.entities.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("SELECT disciplinas FROM Disciplina disciplinas WHERE disciplinas.id = ?1")
    public Disciplina findOne(Long id);
}
