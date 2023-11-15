package com.api.adminschool.entities;

import javax.persistence.*;

@Entity
@Table(name = "turma_disciplinas")
public class TurmaDisciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}
