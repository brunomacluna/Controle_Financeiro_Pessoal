package br.com.brunoluna.controlefinanceiro.dao;

import br.com.brunoluna.controlefinanceiro.model.Usuario;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    public Usuario cadastrarUsuario(Usuario usuario) {

        String sql = "insert into usuarios (login, senha) values (?, ?)";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, senhaCriptografada);

            stmt.executeUpdate();

            try (ResultSet geradorDeId = stmt.getGeneratedKeys()) {
                if (geradorDeId.next()) {
                    int idGerado = geradorDeId.getInt(1);
                    usuario.setId(idGerado);
                }//if
            }//try

            LOG.info("Usuário cadastrado com sucesso! Login: {}, ID: {}", usuario.getLogin(), usuario.getId());

            return usuario;
        }//try

        catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new IllegalArgumentException("Esse Login já está em uso, escolha outro");
            }
            throw new RuntimeException("ERRO AO CADASTRAR USUÁRIO." + e.getMessage(), e);
        }//catch

    }//cadastrarUsuario

}//UsuarioDAO


