package com.api.adminschool.repositories;

import com.api.adminschool.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

//    @Query("SELECT MAX(id) FROM Turma turmas")
//    List<Turma> maxId();
}
