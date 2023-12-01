package com.api.adminschool.repositories;

import com.api.adminschool.entities.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

}
