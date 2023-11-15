package com.api.adminschool.controllers;

import com.api.adminschool.entities.Aluno;
import com.api.adminschool.repositories.AlunoRepository;
import com.api.adminschool.services.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AlunoController {

    private AlunoService alunoService;
    @GetMapping("/alunos")
    public List<?> getAllAlunos() {
        try {
            return alunoService.getAllAlunos();
        } catch (NullPointerException nullValue) {
            List<String> alunosVazio = new ArrayList<>();
            alunosVazio.add("Nenhum aluno encontrado");
            return alunosVazio;
        }
    }
//    @GetMapping("/aluno/{id}")
//    public ResponseEntity<Optional<Aluno>> showAluno(@PathVariable("id") Long id) {
//        return alunoService.showAluno(id);
//    }

}
