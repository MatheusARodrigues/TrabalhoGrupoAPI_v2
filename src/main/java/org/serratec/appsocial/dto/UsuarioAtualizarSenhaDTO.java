package org.serratec.appsocial.dto;

public class UsuarioAtualizarSenhaDTO {
    private String senhaAntiga;
    private String novaSenha;

    // Getters e Setters
    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}

