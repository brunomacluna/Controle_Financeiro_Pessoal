package br.com.brunoluna.controlefinanceiro.model;

import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private String login;
    private String senha;
    private LocalDateTime dataCadastro;

    public Usuario() {
    }//contructor

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }//contructor

    public Usuario(int id, String login, String senha, LocalDateTime dataCadastro) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }//contructor

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}// Usuario
