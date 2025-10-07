package br.com.brunoluna.controlefinanceiro.dao;

import br.com.brunoluna.controlefinanceiro.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void cadastrarUsuario(Usuario usuario) {
        String sql = "insert into usuarios (login, senha , data_cadastro) values (?, ?, ?)";

        try (Connection conn = br.com.brunoluna.controlefinanceiro.dao.ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setObject(3,usuario.getDataCadastro());

            stmt.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            if (e.getMessage().toLowerCase().contains("duplicate key")) {
                System.out.println("Esse login já está em uso. Escolha outro.");
            } else {
                System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
                e.printStackTrace();
            }
        }//try catch

    }//cadastrarUsuario

    public void buscarLogin(Usuario usuario) {
        //falta implementar
    }//buscarLogin

    public void atualizarUsuario(Usuario usuario) {
        String sql = "update usuarios set login = ?, senha = ? where login = ?";

        try (Connection conn = br.com.brunoluna.controlefinanceiro.dao.ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.setSenha());;

        }


    }//atualizarUsusario
}//UsuarioDAO
