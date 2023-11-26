package com.api.adminschool.dtos;

import com.api.adminschool.entities.Aluno;
import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Turma;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TurmaDTO implements Serializable {
    private Long id;
    private String nome;
    private Date createdAt;
    private Date updatedAt;
    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    public TurmaDTO() {}

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

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Turma parseToObject () {
        Turma turma = new Turma();
        turma.setId(this.getId());
        turma.setNome(this.getNome());
        turma.setAlunos(this.getAlunos());
        turma.setDisciplinas(this.getDisciplinas());
        turma.setCreatedAt(this.getCreatedAt());
        turma.setUpdatedAt(this.getUpdatedAt());

        return turma;
    }
}
