package br.com.brunoluna.controlefinanceiro.dao;

import br.com.brunoluna.controlefinanceiro.model.Renda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendaDAO {

    private static final Logger LOG = LoggerFactory.getLogger(RendaDAO.class);

    public Renda cadastrarRenda(Renda renda) {
        if (renda.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("ID de usuáro inválido.");
        }//if
        if (renda.getDescricao() == null || renda.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo descrição não pode ser vazio ou nulo.");
        }//if
        if (renda.getValor() == null || renda.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O campo valor não pode ser negativo ou nulo.");
        }//if

        String sql = "insert into renda (usuario_id, descricao, valor) values (?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, renda.getUsuarioId());
            stmt.setString(2, renda.getDescricao());
            stmt.setBigDecimal(3, renda.getValor());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        renda.setId(idGerado);
                    }//if
                }//try
            }//if
            return renda;
        }//try
        catch (SQLException e) {
           LOG.error("Erro ao cadastrar renda para o usuário do ID: {}", renda.getUsuarioId(), e);
           throw new RuntimeException("Erro ao cadastrar renda no banco de dados.", e);
        }//catch
    }//cadastrarRenda

    public List<Renda> carregarRenda(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido.");
        }//if

        String sql = "select id, usuario_id, descricao, valor, data_criacao from renda where usuario_id = ? order by data_criacao asc";

        ArrayList<Renda> rendaArrayList = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Renda renda = new Renda();
                    renda.setId(rs.getInt("id"));
                    renda.setDescricao(rs.getString("descricao"));
                    renda.setValor(rs.getBigDecimal("valor"));
                    Timestamp timestamp = rs.getTimestamp("data_criacao");
                    renda.setData(timestamp != null ? timestamp.toLocalDateTime() : null);

                    rendaArrayList.add(renda);
                }//while
                return rendaArrayList;
            }//try
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao carregar as rendas do usuárioId: {}", usuarioId, e);
            throw new RuntimeException("Erro ao carregar rendas do banco de dados.", e);
        }//catch
    }//carregarRenda

    public boolean atualizarRenda(Renda renda) {
        if (renda.getId() <= 0 || renda.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("ID de renda ou ID de usuário é inválido");
        }//if
        if (renda.getDescricao() == null || renda.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia.");
        }//if
        if (renda.getValor() == null || renda.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo ou negativo.");
        }//if

        String sql = "update renda set descricao = ?, valor = ? where id = ? and usuario_id = ?";

        try (Connection conn = ConexaoFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, renda.getDescricao());
            stmt.setBigDecimal(2, renda.getValor());
            stmt.setInt(3, renda.getId());
            stmt.setInt(4, renda.getUsuarioId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao atualizar a renda do ID: {}, pertencente ao usuário {}", renda.getId(), renda.getUsuarioId(), e);
            throw new RuntimeException("Erro ao atualizar a tabela renda no banco de dados.", e);
        }//catch
    }//atualizarRenda

    public boolean deletarRenda(Renda renda) {
        if (renda.getId() <=0 || renda.getUsuarioId() <=0) {
            throw new IllegalArgumentException("ID da renda ou ID do usuário é inválido.");
        }//if

        String sql = "delete from renda where id = ? and  usuario_id = ?";

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, renda.getId());
            stmt.setInt(2, renda.getUsuarioId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao deletar as rendas do ID: {}, pertencente ao usuário: {}", renda.getId(), renda.getUsuarioId(), e);
            throw new RuntimeException("Erro ao deletar rendas no banco de dados.");
        }//catch
    }//deletarRenda
}//RendaDAO
