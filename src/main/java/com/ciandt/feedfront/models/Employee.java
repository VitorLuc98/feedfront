package com.ciandt.feedfront.models;

import com.ciandt.feedfront.exceptions.ComprimentoInvalidoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


//TODO: UTILIZE ANOTAÇÕES DO LOMBOK COMO @ALLARGSCONSTRUTOR E RETIRE O QUE NÃO FOR MAIS NECESSÁRIO COMO O CONSTRUTOR COM TODOS OS ARGUMENTOS. DEIXE SEU CÓDIGO MAIS SUSCINTO.

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Feedback> feedbackFeitos;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Feedback> feedbackRecebidos;

    public Employee(String nome, String sobrenome, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }
}