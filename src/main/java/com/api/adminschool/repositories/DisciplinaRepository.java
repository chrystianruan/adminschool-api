package com.api.adminschool.repositories;

import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("SELECT disciplinas FROM Disciplina disciplinas WHERE disciplinas.id = ?1")
    public Disciplina findOne(Long id);
    @Query("SELECT (CASE WHEN COUNT(disciplinas.id) > 0 THEN true else false END) FROM Disciplina disciplinas WHERE disciplinas.professor = ?1")
    public Boolean checkProfessorHasDisciplina(Professor professor);
}
