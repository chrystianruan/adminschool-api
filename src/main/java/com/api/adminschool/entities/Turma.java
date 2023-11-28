package com.api.adminschool.entities;

import com.api.adminschool.dtos.TurmaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity

@Table(name = "turmas")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @JsonIgnore
    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "turma_disciplinas",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas;

    public List<Aluno> getAlunos() {
        return alunos;
    }


    public Turma() {}

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

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
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

    public TurmaDTO parseToDTO () {
        TurmaDTO turmaDTO = new TurmaDTO();
        turmaDTO.setId(this.getId());
        turmaDTO.setNome(this.getNome());
        turmaDTO.setAlunos(this.getAlunos());
        turmaDTO.setDisciplinas(this.getDisciplinas());
        turmaDTO.setCreatedAt(this.getCreatedAt());
        turmaDTO.setUpdatedAt(this.getUpdatedAt());

        return turmaDTO;
    }


}
