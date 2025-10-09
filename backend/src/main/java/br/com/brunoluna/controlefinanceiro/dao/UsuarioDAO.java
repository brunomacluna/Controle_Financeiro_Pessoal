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

    public Usuario buscarPorLogin(String login) {
        String sql = "select id, login, senha, data_cadastro from usuarios where login = ?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                  Usuario usuario = new Usuario();
                  usuario.setId(rs.getInt("id"));
                  usuario.setLogin(rs.getString("login"));
                  usuario.setSenha(rs.getString("senha"));
                  Timestamp timestamp = rs.getTimestamp("data_cadastro");
                  usuario.setDataCadastro(timestamp != null ? timestamp.toLocalDateTime() : null);
                  return usuario;
                } else {
                    return null;
                }//if else
            }//try

        }//try
        catch (SQLException e) {
            LOG.error("Erro ao buscar usuário por login: {}", login, e);
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e);
        }//catch
    }//buscarPorLogin

    public Usuario buscarPorId(int id) {
        String sql = "select id, login, senha, data_cadastro from usuarios where id = ?";

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    Timestamp timestamp = rs.getTimestamp("data_cadastro");
                    usuario.setDataCadastro(timestamp != null ? timestamp.toLocalDateTime() : null);
                    return usuario;
                } else {
                    return null;
                }//if else
            }//try

        }//try
        catch (SQLException e) {
            LOG.error("Erro ao buscar usuário por id: {}", id, e);
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e);
        }//catch
    }//buscarPorId

    public boolean atualizarSenha(Usuario usuario) {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido!");
        }//if

        String sql = "update usuarios set senha = ? where id = ?";

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

            stmt.setString(1, senhaCriptografada);
            stmt.setInt(2, usuario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao atualizar senha do usuário do id {}", usuario.getId(), e);
            throw new RuntimeException("Erro ao atualizar senha no banco de dados.", e);
        }//catch
    }//atualizarSenha

    public boolean deletarUsuario(Usuario usuario) {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido!");
        }//if

        String sql = "delete from usuarios where id = ?";

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, usuario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao deletar o usuário do id {}", usuario.getId(), e);
            throw new RuntimeException("Erro ao deletar usuário do banco de dados.", e);
        }//catch
    }//deletarUsuario
}//UsuarioDAO


