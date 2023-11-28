package com.api.adminschool.services;

import com.api.adminschool.dtos.ProfessorDTO;
import com.api.adminschool.entities.Professor;
import com.api.adminschool.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    public List<?> getAllProfessores() {
        try {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Nenhum item encontrado");

            if (professorRepository.findAll().isEmpty()) {
                return emptyList;
            } else {
                professorRepository.findAll();
            }

            List<ProfessorDTO> professores = new ArrayList<>();
            for (Professor professor : professorRepository.findAll()) {
                ProfessorDTO professorDTO = professor.parseToDTO();
                professores.add(professorDTO);
            }

            return professores;

        } catch (RuntimeException runex) {
            throw new RuntimeException(runex);
        }
    }

    public ResponseEntity<String> newProfessor(ProfessorDTO professorDTO) {
        try {
            Professor professor = new Professor();
            professor.setNome(professorDTO.getNome());
            professor.setCreatedAt(new Date());
            professor.setUpdatedAt(new Date());
            professorRepository.save(professor);

            return new ResponseEntity<String>("O professor "+professorDTO.getNome()+" foi inserido com sucesso!", HttpStatus.CREATED);
        } catch (Error error) {
            throw new Error();
        }
    }

    public ResponseEntity<?> showProfessor(Long id) {
        try {
            if (professorRepository.findById(id).isEmpty()) {
                return new ResponseEntity<String>("Nenhum registro encontrado", HttpStatus.NOT_FOUND);
            }
            ProfessorDTO professorDTO = professorRepository.findById(id).get().parseToDTO();

            return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);

        } catch (Error error) {
            throw new Error();
        }
    }

    public ResponseEntity<?> deleteProfessor(Long id) {
       try {
           if (professorRepository.findById(id).isEmpty()) {
               return new ResponseEntity<String>("Nenhum registro encontrado", HttpStatus.NOT_FOUND);
           }
           String nome = professorRepository.findById(id).get().getNome();
           professorRepository.deleteById(id);

           return new ResponseEntity<String>("Professor "+nome+" foi apagado com sucesso", HttpStatus.OK);
        } catch (Error error) {
            throw new Error(error);
        }
    }
    @Transactional
    public ResponseEntity<?> updateProfessor(Long id, ProfessorDTO professorDTO) {
        try {
            if (professorRepository.findById(id).isEmpty()) {
                return new ResponseEntity<String>("Professor não encontrado na base de dados", HttpStatus.NOT_FOUND);
            }

            return professorRepository.findById(id)
                    .map(professor -> {
                        professor.setNome(professorDTO.getNome());
                        professor.setUpdatedAt(new Date());
                        professorRepository.save(professor);
                        return new ResponseEntity<String>("Professor atualizado com sucesso", HttpStatus.OK);
                    }).orElse(new ResponseEntity<String>("Erro encontrado ao tentar atualizar professor", HttpStatus.BAD_GATEWAY));
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }
}
