package com.api.adminschool.services;

import com.api.adminschool.dtos.AlunoDTO;
import com.api.adminschool.entities.Aluno;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.repositories.AlunoRepository;
import com.api.adminschool.repositories.TurmaRepository;
import com.sun.source.doctree.SinceTree;
import org.hibernate.type.OneToOneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    public List<?> getAllAlunos() {
        try {
            List<String> listaVazia = new ArrayList<>();
            listaVazia.add("Nenhum aluno cadastrado até o momento");
            if (alunoRepository.findAll().isEmpty()) {
                return listaVazia;
            }
            List<AlunoDTO> alunosResponse = new ArrayList<>();
            for (Aluno aluno : alunoRepository.findAll()) {
                AlunoDTO alunoDTO = aluno.parseToDTO();
                alunosResponse.add(alunoDTO);
            }

            return alunosResponse;
        } catch (RuntimeException run) {
            throw new RuntimeException(run);
        }

    }
    public ResponseEntity<AlunoDTO> showAluno(Long id) {
        try {
            Optional<Aluno> aluno = alunoRepository.findById(id);

            if (aluno.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            AlunoDTO alunoDTO = aluno.get().parseToDTO();
            return new ResponseEntity<AlunoDTO>(alunoDTO, HttpStatus.OK);
        } catch (RuntimeException run) {
            throw new RuntimeException(run);
        }
    }

    public ResponseEntity<?> saveAluno(AlunoDTO alunoDTO) {
        try {
            String matricula = generateMatricula();
            if (!turmaRepository.checkTurmaExists(alunoDTO.getTurmaID())) {
                return new ResponseEntity<String>("A turma selecionada não existe", HttpStatus.NOT_FOUND);
            }
            if (alunoRepository.checkMatriculaExists(matricula)) {
                this.saveAluno(alunoDTO);
            }
            alunoDTO.setMatricula(matricula);
            Turma requestTurma = turmaRepository.findOne(alunoDTO.getTurmaID());
            alunoDTO.setTurmaObj(requestTurma);
            alunoDTO.setTurmaName(requestTurma.getNome());

            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setMatricula(matricula);
            aluno.setDataNascimento(alunoDTO.getDataNascimento());
            aluno.setTurma(requestTurma);
            aluno.setCreatedAt(new Date());
            aluno.setUpdatedAt(new Date());
            alunoRepository.save(aluno);

            requestTurma.getAlunos().add(aluno);

            Map<String, String> response = new HashMap<>();
            response.put("nome", aluno.getNome());
            response.put("matricula", aluno.getMatricula());
            response.put("turma", aluno.getTurma().getNome());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }

    }

    public String generateMatricula() {
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String finalNumber = Integer.toString(random.nextInt(90000) + 10000);
        return year+finalNumber;
    }

    public ResponseEntity<?> deleteAluno(String matricula) {
        try {
            if (!alunoRepository.checkMatriculaExists(matricula)) {
                return new ResponseEntity<String>("Matrícula não existe na base de dados", HttpStatus.NOT_FOUND);
            }
            Long alunoID = alunoRepository.findByMatricula(matricula).getId();
            alunoRepository.deleteById(alunoID);
            return new ResponseEntity<String>("Aluno apagado com sucesso!", HttpStatus.OK);
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }

    @Transactional
    public ResponseEntity<?> updateAluno(String matricula, AlunoDTO alunoDTO) {
        try {
            if (!alunoRepository.checkMatriculaExists(matricula)) {
                return new ResponseEntity<String>("Matrícula não existe na base de dados", HttpStatus.NOT_FOUND);
            }
            if (!turmaRepository.checkTurmaExists(alunoDTO.getTurmaID())) {
                return new ResponseEntity<String>("Turma não existe", HttpStatus.NOT_FOUND);
            }
            Aluno alunoObj = alunoRepository.findByMatricula(matricula);
            Turma turma = turmaRepository.findOne(alunoDTO.getTurmaID());
            return alunoRepository.findById(alunoObj.getId())
                    .map(aluno -> {
                        aluno.setTurma(turma);
                        aluno.setUpdatedAt(new Date());
                        //TODO: fazer adicao no metodo alunos da model Turma
                        alunoRepository.save(aluno);
                        return new ResponseEntity<String>("Aluno atualizado com sucesso", HttpStatus.OK);
                    }).orElse(new ResponseEntity<String>("Erro encontrado ao tentar atualizar aluno", HttpStatus.BAD_GATEWAY));
        } catch (RuntimeException runEx) {
            throw new RuntimeException(runEx);
        }
    }
}
