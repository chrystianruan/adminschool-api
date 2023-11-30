package com.api.adminschool.entities;

import com.api.adminschool.dtos.ProfessorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "professores")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @JsonIgnore
    @OneToOne(mappedBy = "professor")
    private Disciplina disciplina;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Professor() {}


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
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

    public ProfessorDTO parseToDTO() {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(this.getId());
        dto.setNome(this.getNome());
        dto.setDisciplina(this.getDisciplina());
        dto.setCreatedAt(this.getCreatedAt());
        dto.setUpdateAt(this.getUpdatedAt());

        return dto;
    }
}
