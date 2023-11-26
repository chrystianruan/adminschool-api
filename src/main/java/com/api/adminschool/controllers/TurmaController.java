package com.api.adminschool.controllers;

import com.api.adminschool.dtos.TurmaDTO;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TurmaController {
    @Autowired
    private TurmaService turmaService;

    @GetMapping(value = "/turmas")
    public List<?> getAllTurmas() {
        return turmaService.getAllTurmas();
    }

    @GetMapping(value = "/turma/{id}")
    public ResponseEntity<TurmaDTO> showTurma(@PathVariable("id") Long id) {
        return turmaService.getTurmaById(id);
    }

    @PostMapping(value = "/salvar-turma")
    public ResponseEntity<Turma> saveTurma(@Valid @RequestBody Turma turma) {
        return turmaService.saveTurma(turma);
    }

    @DeleteMapping(value = "/deletar-turma/{id}")
    public ResponseEntity<Optional<Turma>> deleteTurma(@PathVariable("id") Long id) {
        return turmaService.deleteTurma(id);
    }

    @PutMapping(value = "/atualizar-turma/{id}")
    public ResponseEntity<Turma> updateTurma(@PathVariable("id") Long id, @Valid @RequestBody TurmaDTO turma) {
        return turmaService.updateTurma(id, turma);
    }

}
