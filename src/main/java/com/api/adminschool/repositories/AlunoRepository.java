package com.api.adminschool.repositories;

import com.api.adminschool.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("SELECT alunos FROM Aluno alunos WHERE alunos.id = ?1")
    public Aluno findOne(Long id);

    @Query("SELECT case when count(alunos.id) > 0 then true else false end FROM Aluno alunos WHERE alunos.matricula LIKE UPPER(CONCAT('%', :matricula, '%')) ")
    public Boolean checkMatriculaExists(String matricula);

    @Query("SELECT alunos FROM Aluno alunos WHERE alunos.matricula LIKE UPPER(CONCAT('%', :matricula, '%'))")
    public Aluno findByMatricula(String matricula);
}
