package br.com.brunoluna.controlefinanceiro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacao {

    private int id;
    private Usuario usuario;
    private String tipo;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public Transacao() {
    }//constructor

    public Transacao(int id, Usuario usuario, String tipo, String descricao, BigDecimal valor, LocalDate data) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }//constructor

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}//Transacoes
