package com.api.adminschool.entities;

import com.api.adminschool.dtos.DisciplinaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "disciplinas")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @JsonIgnore
    @ManyToMany(mappedBy = "disciplinas")
    private List<Turma> turmas;

    public Disciplina() {}

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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
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

    public DisciplinaDTO parseToDTO() {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setId(this.getId());
        dto.setNome(this.getNome());
        dto.setTurmas(this.getTurmas());
        dto.setProfessor(this.getProfessor());
        dto.setProfessorID(this.getProfessor().getId());
        dto.setCreatedAt(this.getCreatedAt());
        dto.setUpdatedAt(this.getUpdatedAt());

        return dto;
    }
}
