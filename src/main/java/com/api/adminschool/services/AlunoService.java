package com.api.adminschool.services;

import com.api.adminschool.entities.Aluno;
import com.api.adminschool.repositories.AlunoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    private AlunoRepository alunoRepository;

    public List<?> getAllAlunos() {
        return alunoRepository.findAll();
    }

//    public ResponseEntity<Optional<Aluno>> showAluno(Long id) {
//        try {
//
//        } catch (HttpClientErrorException.NotFound notFound) {
//
//        }
//    }
}
