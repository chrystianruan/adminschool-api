package com.api.adminschool.services;

import com.api.adminschool.dtos.DisciplinaDTO;
import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Professor;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.repositories.DisciplinaRepository;
import com.api.adminschool.repositories.ProfessorRepository;
import com.api.adminschool.repositories.TurmaDisciplinaRepository;
import com.api.adminschool.repositories.TurmaRepository;
import com.api.adminschool.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.*;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;
    @Autowired
    private ResponseUtils responseUtils;

    public Map<String,String> response;
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
                Map<String,String> response = responseUtils.getResponseMessage("Disciplina não localizada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            DisciplinaDTO dto = disciplinaRepository.findOne(id).parseToDTO();

            return new ResponseEntity<DisciplinaDTO>(dto, HttpStatus.OK);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    public ResponseEntity<?> saveDisciplina(DisciplinaDTO requestDisciplina) {
        Professor prof = new Professor();

        if (requestDisciplina.getProfessorID() != null) {
            if (professorRepository.findById(requestDisciplina.getProfessorID()).isEmpty()) {
                response = responseUtils.getResponseMessage("O professor selecionado não existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            if (disciplinaRepository.checkProfessorHasDisciplina(professorRepository.findOne(requestDisciplina.getProfessorID()))) {
                response = responseUtils.getResponseMessage("O professor já ministra a disciplina de "+professorRepository.findOne(requestDisciplina.getProfessorID()).getDisciplina().getNome()+" e não pode ser inserido nesta disciplina");
                return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
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
                    response = responseUtils.getResponseMessage("As disciplinas não existem na base de dados");
                    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
            disciplinaRepository.save(disciplina);

            prof.setDisciplina(disciplina);
            response = responseUtils.getResponseMessage("Disciplina "+requestDisciplina.getNome()+" cadastrada com sucesso!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    public ResponseEntity<?> deleteDisciplina(Long id) {
        try {
            if (disciplinaRepository.findById(id).isEmpty()) {
                response = responseUtils.getResponseMessage("Disciplina não encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            disciplinaRepository.deleteById(id);
            response = responseUtils.getResponseMessage("Disciplina deleteda com sucesso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }
    @Transactional
    public ResponseEntity<String> updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
        try {
            return disciplinaRepository.findById(id)
                    .map(disciplina -> {
                        disciplina.setNome(disciplinaDTO.getNome());
                        disciplina.setUpdatedAt(new Date());
                        if (disciplinaDTO.getProfessorID() != null) {
                            if (professorRepository.findById(disciplinaDTO.getProfessorID()).isEmpty()) {
                                return new ResponseEntity<String>("O professor selecionado não existe", HttpStatus.NOT_FOUND);
                            }
                            Professor prof = professorRepository.findOne(disciplinaDTO.getProfessorID());
                            disciplina.setProfessor(prof);
                        }
                        if (disciplinaDTO.getTurmasID() != null) {
                            if (!this.getTurmasByIds(disciplinaDTO.getTurmasID()).isEmpty()) {
                                this.clearDisciplinasOfTurma(disciplina.getTurmas(), disciplina);
                                List<Turma> turmas = this.getTurmasByIds(disciplinaDTO.getTurmasID());
                                disciplina.setTurmas(turmas);
                                this.insertDisciplinaIntoTurmas(disciplina, turmas);
                            } else {
                                return new ResponseEntity<String>("As disciplinas não existem na base de dados", HttpStatus.METHOD_NOT_ALLOWED);
                            }
                        }
                        disciplinaRepository.save(disciplina);

                        return new ResponseEntity<String>("Disciplina atualizada com sucesso", HttpStatus.ACCEPTED);
                    }).orElse(new ResponseEntity<String>("Erro ao atualizar disciplina", HttpStatus.METHOD_NOT_ALLOWED));
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

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

    public void clearDisciplinasOfTurma(List<Turma> turmas, Disciplina disciplina) {
        for (Turma turma : turmas) {
            turma.getDisciplinas().remove(disciplina);
        }
    }



}
