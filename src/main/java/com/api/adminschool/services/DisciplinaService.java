package com.api.adminschool.services;

import com.api.adminschool.dtos.DisciplinaDTO;
import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.repositories.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
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


}
