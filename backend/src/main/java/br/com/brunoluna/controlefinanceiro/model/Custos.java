package br.com.brunoluna.controlefinanceiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Custos {

    private int id;
    private int usuarioId;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime data;

    public Custos() {
    }//constructor

    public Custos(int id, int usuarioId, String descricao, BigDecimal valor, LocalDateTime data) {
        this.id = id;
        this.usuarioId = usuarioId;
        setDescricao(descricao);
        setValor(valor);
        this.data = data;
    }//constructor

    //getters
    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    //setters
    public void setDescricao(String descricao) {
        if (descricao == null){
            throw new IllegalArgumentException("A descrição não pode ser null.");
        }

        String descricaoLimpa = descricao.trim();

        if (descricaoLimpa.isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia ou conter apenas espaços.");
        }

        if (descricaoLimpa.length() > 255) {
            throw new IllegalArgumentException("Descrição muito longa (máximo 255 caracteres)");
        }

        this.descricao = descricaoLimpa;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor não pode ser null.");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor deve ser maior ou igual a zero.");
        }
        if (valor.scale() > 2) {
            throw new IllegalArgumentException("O valor não pode ter mais que duas casas decimais.");
        }
        this.valor = valor;
    }
}//Custos
