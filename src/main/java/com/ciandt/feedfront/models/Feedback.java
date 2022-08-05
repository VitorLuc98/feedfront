package com.ciandt.feedfront.models;

import com.ciandt.feedfront.exceptions.ComprimentoInvalidoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

//TODO: UTILIZE ANOTAÇÕES DO LOMBOK COMO @ALLARGSCONSTRUTOR E RETIRE O QUE NÃO FOR MAIS NECESSÁRIO COMO O CONSTRUTOR COM TODOS OS ARGUMENTOS. DEIXE SEU CÓDIGO MAIS SUSCINTO.



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;

    @Column
    private String oQueMelhora;

    @Column
    private String comoMelhora;

    @Column
    private LocalDate data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Employee autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

}
