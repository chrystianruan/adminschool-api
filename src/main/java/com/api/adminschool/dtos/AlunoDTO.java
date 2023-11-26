package com.api.adminschool.dtos;

import com.api.adminschool.entities.Aluno;
import com.api.adminschool.entities.Turma;
import com.api.adminschool.repositories.TurmaRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
public class AlunoDTO implements Serializable {
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("matricula")
    private String matricula;
    @JsonProperty("data_nascimento")
    private Date dataNascimento;
    @JsonProperty("turma_id")
    private Long turmaID;
    private Turma turmaObj;
    private String turmaName;
    private Date createdAt;
    private Date updatedAt;
    public AlunoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getTurmaID() {
        return turmaID;
    }

    public void setTurmaID(Long turmaID) {
        this.turmaID = turmaID;
    }

    public Turma getTurmaObj() {
        return turmaObj;
    }
    public void setTurmaObj(Turma turmaObj) {
        this.turmaObj = turmaObj;
    }
    public String getTurmaName() {
        return turmaName;
    }
    public void setTurmaName(String turmaName) {
        this.turmaName = turmaName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Aluno parseToObject() {
        Aluno aluno = new Aluno();
        aluno.setId(this.getId());
        aluno.setNome(this.getNome());
        aluno.setMatricula(this.getMatricula());
        aluno.setTurma(this.getTurmaObj());
        aluno.setDataNascimento(this.getDataNascimento());
        aluno.setCreatedAt(this.getCreatedAt());
        aluno.setUpdatedAt(this.getUpdatedAt());

        return aluno;
    }
}
