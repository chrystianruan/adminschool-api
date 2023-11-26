package com.api.adminschool.controllers;

import com.api.adminschool.dtos.AlunoDTO;
import com.api.adminschool.dtos.TurmaDTO;
import com.api.adminschool.entities.Aluno;
import com.api.adminschool.repositories.AlunoRepository;
import com.api.adminschool.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;
    @GetMapping("/alunos")
    public List<?> getAllAlunos() {
        return alunoService.getAllAlunos();
    }
    @GetMapping("/aluno/{id}")
    public ResponseEntity<AlunoDTO> showAluno(@PathVariable("id") Long id) {
        return alunoService.showAluno(id);
    }

    @PostMapping("/salvar-aluno")
    public ResponseEntity<?> saveAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        return alunoService.saveAluno(alunoDTO);
    }
    @DeleteMapping("/deletar-aluno/{matricula}")
    public ResponseEntity<?> deleteAluno(@PathVariable("matricula") String matricula) {
        return alunoService.deleteAluno(matricula);
    }
    @PutMapping("/atualizar-aluno/{matricula}")
    public ResponseEntity<?> updateAluno(@PathVariable("matricula") String matricula, @RequestBody @Valid AlunoDTO alunoDTO) {
        return alunoService.updateAluno(matricula, alunoDTO);
    }
}
