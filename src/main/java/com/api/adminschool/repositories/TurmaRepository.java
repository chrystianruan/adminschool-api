package com.api.adminschool.repositories;

import com.api.adminschool.entities.Aluno;
import com.api.adminschool.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT turmas FROM Turma turmas WHERE turmas.id = ?1")
    public Turma findOne(Long id);
    @Query("SELECT case when count(turmas.id) > 0 then true else false end FROM Turma turmas WHERE turmas.id = ?1")
    public boolean checkTurmaExists(Long id);
}
