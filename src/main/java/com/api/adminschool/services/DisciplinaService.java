package com.api.adminschool.services;

import com.api.adminschool.dtos.DisciplinaDTO;
import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Professor;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.repositories.DisciplinaRepository;
import com.api.adminschool.repositories.ProfessorRepository;
import com.api.adminschool.repositories.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    public List<?> getAllDisciplinas() {
        try {
            List<String> listaVazia = new ArrayList<>();
            listaVazia.add("Nenhum disciplina cadastrado até o momento");
            if (disciplinaRepository.findAll().isEmpty()) {
                return listaVazia;
            }
            List<DisciplinaDTO> disciplinasResponse = new ArrayList<>();
            for (Disciplina disciplina : disciplinaRepository.findAll()) {
                DisciplinaDTO dto = disciplina.parseToDTO();
                disciplinasResponse.add(dto);
            }

            return disciplinasResponse;
        } catch (RuntimeException run) {
            throw new RuntimeException(run);
        }
    }

    public ResponseEntity<?> showDisciplina(Long id) {
        try {
            if(disciplinaRepository.findById(id).isEmpty()) {
                return new ResponseEntity<String>("Disciplina não localizada", HttpStatus.NOT_FOUND);
            }
            DisciplinaDTO dto = disciplinaRepository.findById(id).get().parseToDTO();

            return new ResponseEntity<DisciplinaDTO>(dto, HttpStatus.OK);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    public ResponseEntity<String> saveDisciplina(DisciplinaDTO requestDisciplina) {
        Professor prof = new Professor();
        if (requestDisciplina.getProfessorID() != null) {
            if (professorRepository.findById(requestDisciplina.getProfessorID()).isEmpty()) {
                return new ResponseEntity<String>("O professor selecionado não existe", HttpStatus.NOT_FOUND);
            }
            prof = professorRepository.findOne(requestDisciplina.getProfessorID());
        }
        try {
            Disciplina disciplina = new Disciplina();
            disciplina.setNome(requestDisciplina.getNome());
            disciplina.setCreatedAt(new Date());
            disciplina.setUpdatedAt(new Date());
            disciplina.setProfessor(prof);
            if (requestDisciplina.getTurmasID() != null) {
                if (!this.getTurmasByIds(requestDisciplina.getTurmasID()).isEmpty()) {
                    List<Turma> turmas = this.getTurmasByIds(requestDisciplina.getTurmasID());
                    disciplina.setTurmas(turmas);
                    this.insertDisciplinaIntoTurmas(disciplina, turmas);
                } else {
                    return new ResponseEntity<String>("As disciplinas não existem na base de dados", HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
            disciplinaRepository.save(disciplina);

            prof.setDisciplina(disciplina);

            return new ResponseEntity<String>("Disciplina "+requestDisciplina.getNome()+" cadastrada com sucesso!", HttpStatus.CREATED);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    public ResponseEntity<String> deleteDisciplina(Long id) {
        try {
            if (disciplinaRepository.findById(id).isEmpty()) {
                return new ResponseEntity<String>("Disciplina não encontrada", HttpStatus.NOT_FOUND);
            }
            disciplinaRepository.deleteById(id);
            return new ResponseEntity<String>("Disciplina deleteda com sucesso", HttpStatus.OK);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

//    public ResponseEntity<String> updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
//        try {
//            return disciplinaRepository.findById(id)
//                    .map(disciplina -> {
//                        disciplina.setNome(disciplinaDTO.getNome());
//                        disciplina.setTurmas(disciplinaDTO.getTurmas());
//                        disciplina.setUpdatedAt(new Date());
//                        if (disciplinaDTO.getProfessorID() != null) {
//                            if (professorRepository.findById(disciplinaDTO.getProfessorID()).isEmpty()) {
//                                return new ResponseEntity<String>("O professor selecionado não existe", HttpStatus.NOT_FOUND);
//                            }
//                            Professor prof = professorRepository.findOne(disciplinaDTO.getProfessorID());
//                            disciplina.setProfessor(prof);
//                        }
//                        if (disciplinaDTO.getTurmasID() != null) {
//                            if (!this.getTurmasByIds(disciplinaDTO.getTurmasID()).isEmpty()) {
//                                disciplina.getTurmas().clear();
//                                List<Turma> turmas = this.getTurmasByIds(disciplinaDTO.getTurmasID());
//                                disciplina.setTurmas(turmas);
//                                this.insertDisciplinaIntoTurmas(disciplina, turmas);
//                            } else {
//                                return new ResponseEntity<String>("As disciplinas não existem na base de dados", HttpStatus.METHOD_NOT_ALLOWED);
//                            }
//                        }
//                        disciplinaRepository.save(disciplina);
//
//                        return new ResponseEntity<String>("Disciplina atualizada com sucesso", HttpStatus.ACCEPTED);
//                    }).orElse(new ResponseEntity<String>("Erro ao atualizar disciplina", HttpStatus.METHOD_NOT_ALLOWED));
//        } catch (RuntimeException runEx) {
//            throw new RuntimeException(runEx);
//        }
//    }

    public List<Turma> getTurmasByIds(List<Long> turmasID) {
        List<Turma> turmas = new ArrayList<>();
        for (Long i : turmasID) {
            if (!turmaRepository.findById(i).isEmpty()) {
                turmas.add(turmaRepository.findOne(i));
            }
        }
        return turmas;
    }

    public void insertDisciplinaIntoTurmas(Disciplina disciplina, List<Turma> turmas) {
        for (Turma turma : turmas) {
            turma.getDisciplinas().add(disciplina);
        }
    }



}
