package com.api.adminschool.dtos;
import com.api.adminschool.entities.Disciplina;
import com.api.adminschool.entities.Professor;

import java.util.Date;

public class ProfessorDTO {
    private Long id;
    private String nome;
    private Date createdAt;
    private Date updateAt;
    private Disciplina disciplina;

    public ProfessorDTO() { }

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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor parseToObject() {
        Professor prof = new Professor();
        prof.setId(this.getId());
        prof.setNome(this.getNome());
        prof.setDisciplina(this.getDisciplina());
        prof.setCreatedAt(this.getCreatedAt());
        prof.setUpdatedAt(this.getUpdateAt());

        return prof;
    }
}
