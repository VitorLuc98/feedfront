package com.ciandt.feedfront.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;



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
    @Size(min = 3, message = "O Nome deve ter mais de 2 caracteres")
    private String nome;

    @Column(nullable = false)
    @Size(min = 3, message = "O Sobrenome deve ter mais de 2 caracteres")
    private String sobrenome;

    @Column(unique = true)
    @Email(message = "Email inválido.")
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