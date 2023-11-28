package com.api.adminschool.controllers;

import com.api.adminschool.dtos.ProfessorDTO;
import com.api.adminschool.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    @GetMapping("/professores")
    public List<?> getAllProfessores() {
        return professorService.getAllProfessores();
    }
    @PostMapping("/salvar-professor")
    public ResponseEntity<?> newProfessor(@RequestBody ProfessorDTO professorDTO) {
        return professorService.newProfessor(professorDTO);
    }
    @GetMapping("/professor/{id}")
    public ResponseEntity<?> showProfessor(@PathVariable("id") Long id) {
        return professorService.showProfessor(id);
    }
    @DeleteMapping("/delete-prof/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Long id) {
        return professorService.deleteProfessor(id);
    }
    @PutMapping("/atualizar-professor/{id}")
    public ResponseEntity<?> atualizarProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDTO professorDTO) {
        return professorService.updateProfessor(id, professorDTO);
    }
}
