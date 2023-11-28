package com.api.adminschool.entities;

import com.api.adminschool.dtos.AlunoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    private String nome;
    private String matricula;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Aluno() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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

    public AlunoDTO parseToDTO() {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(this.getId());
        alunoDTO.setNome(this.getNome());
        alunoDTO.setMatricula(this.getMatricula());
        alunoDTO.setDataNascimento(this.getDataNascimento());
        alunoDTO.setTurmaName(this.getTurma().getNome());
        alunoDTO.setTurmaObj(this.getTurma());
        alunoDTO.setCreatedAt(this.getCreatedAt());
        alunoDTO.setUpdatedAt(this.getUpdatedAt());
        alunoDTO.setTurmaID(this.getTurma().getId());

        return alunoDTO;
    }
}
