package com.ciandt.feedfront.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1l;

    private String id;
    private LocalDate data;
    private Employee autor;
    private Employee proprietario;
    private String descricao;
    private String oqueMelhora;
    private String comoMelhora;
    private String arquivo;

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oqueMelhora, String comoMelhora) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
        this.oqueMelhora = oqueMelhora;
        this.comoMelhora = comoMelhora;
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOqueMelhora() {
        return oqueMelhora;
    }

    public void setOqueMelhora(String oqueMelhora) {
        this.oqueMelhora = oqueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    public String getArquivo() {
        return arquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;

        Feedback feedback = (Feedback) o;

        return id != null ? id.equals(feedback.id) : feedback.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", data=" + data +
                ", autor=" + autor +
                ", proprietario=" + proprietario +
                ", descricao='" + descricao + '\'' +
                ", oqueMelhora='" + oqueMelhora + '\'' +
                ", comoMelhora='" + comoMelhora + '\'' +
                ", arquivo='" + arquivo + '\'' +
                '}';
    }
}
