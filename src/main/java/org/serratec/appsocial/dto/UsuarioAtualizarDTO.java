package org.serratec.appsocial.dto;

import java.time.LocalDate;

public class UsuarioAtualizarDTO {
    private String nome;
    private LocalDate dataNascimento;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

