package com.api.adminschool.services;

import com.api.adminschool.dtos.TurmaDTO;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.repositories.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    public List<?> getAllTurmas() {
        try {
            List<String> listaVazia = new ArrayList<>();
            listaVazia.add("Nenhum aluno cadastrado até o momento");
            List<Turma> turmas = turmaRepository.findAll();
            if (turmas.isEmpty()) {
                return listaVazia;
            }
            List<TurmaDTO> turmaDTOS = new ArrayList<>();
            for (Turma turma : turmas) {
                TurmaDTO dto = turma.parseToDTO();
                turmaDTOS.add(dto);
            }
            return turmaDTOS;
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    public ResponseEntity<TurmaDTO> getTurmaById(Long id) {
        try {
            Optional<Turma> turma = turmaRepository.findById(id);
            TurmaDTO dto = turma.get().parseToDTO();
            return new ResponseEntity<TurmaDTO>(dto, HttpStatus.OK);
        } catch (HttpClientErrorException.NotFound notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public ResponseEntity<Turma> saveTurma(Turma turma) {
        turmaRepository.save(turma);
        return new ResponseEntity<Turma>(HttpStatus.CREATED);
    }
    public ResponseEntity<Optional<Turma>> deleteTurma(Long id) {
       try {
           turmaRepository.deleteById(id);
           return new ResponseEntity<Optional<Turma>>(HttpStatus.OK);
       } catch (HttpClientErrorException.NotAcceptable notAllowed) {
           return new ResponseEntity<Optional<Turma>>(HttpStatus.METHOD_NOT_ALLOWED);
       }
    }
    @Transactional
    public ResponseEntity<Turma> updateTurma(Long id, TurmaDTO turmaDTO) {
        Date now = new Date();
        return turmaRepository.findById(id)
                .map(turma -> {
                    turma.setNome(turmaDTO.getNome());
                    turma.setUpdatedAt(now);
                    Turma turmaUp = turmaRepository.save(turma);
                    return ResponseEntity.ok().body(turmaUp);
                }).orElse(ResponseEntity.notFound().build());
    }
}
