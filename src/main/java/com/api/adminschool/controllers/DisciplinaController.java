package com.api.adminschool.controllers;

import com.api.adminschool.dtos.DisciplinaDTO;
import com.api.adminschool.services.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;
    @GetMapping("/disciplinas")
    public List<?> getAllDisciplinas() {
        return disciplinaService.getAllDisciplinas();
    }
    @PostMapping("/salvar_disciplina")
    public ResponseEntity<?> saveDisciplina(@RequestBody DisciplinaDTO dto) {
        return disciplinaService.saveDisciplina(dto);
    }
    @GetMapping("/disciplina/{id}")
    public ResponseEntity<?> showDisciplina(@PathVariable("id") Long id) {
        return disciplinaService.showDisciplina(id);
    }
    @DeleteMapping("/delete-disciplina/{id}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable("id") Long id) {
        return disciplinaService.deleteDisciplina(id);
    }
    @PutMapping("/atualizar-disciplina/{id}")
    public ResponseEntity<String> atualizarDisciplina(@PathVariable("id") Long id, @RequestBody DisciplinaDTO dto) {
        return disciplinaService.updateDisciplina(id, dto);
    }
}
