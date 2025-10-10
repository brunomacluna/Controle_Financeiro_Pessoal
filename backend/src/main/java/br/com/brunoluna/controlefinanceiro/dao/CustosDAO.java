package br.com.brunoluna.controlefinanceiro.dao;

import br.com.brunoluna.controlefinanceiro.model.Custos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustosDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CustosDAO.class);

    public Custos cadastarCustos(Custos custos) {
        if (custos.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido!");
        }//if
        if (custos.getDescricao() == null || custos.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo descrição não pode ser vazio ou nulo.");
        }//if
        if (custos.getValor() == null || custos.getValor().compareTo(BigDecimal.ZERO) < 0) {
           throw new IllegalArgumentException("O campo valor não pode ser negativo ou nulo.");
        }//if

        String sql = "insert into custos (usuario_id, descricao, valor) values (?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, custos.getUsuarioId());
            stmt.setString(2, custos.getDescricao());
            stmt.setBigDecimal(3, custos.getValor());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        custos.setId(idGerado);
                    }//if
                }//try
            }//if
            return custos;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao cadastrar custo para o usuário do ID: {}", custos.getUsuarioId(), e);
            throw new RuntimeException("Erro ao cadastrar custo no banco de dados", e);
        }//catch
    }//cadastarCustos

    public List<Custos> buscarPorUsuarioIdCustos(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido.");
        }//if
        //eu quero que os custos mais antigos fiquem no topo da tabela
        String sql = "select id, usuario_id, descricao, valor, data_criacao from custos where usuario_id = ? order by data_criacao asc";

        ArrayList<Custos> custosArrayList = new ArrayList<>();

        try (Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Custos custos = new Custos();
                    custos.setId(rs.getInt("id"));
                    custos.setUsuarioId(rs.getInt("usuario_id"));
                    custos.setDescricao(rs.getString("descricao"));
                    custos.setValor(rs.getBigDecimal("valor"));
                    Timestamp timestamp = rs.getTimestamp("data_criacao");
                    custos.setData(timestamp != null ? timestamp.toLocalDateTime() : null);

                    custosArrayList.add(custos);
                }//while
            }//try
            return custosArrayList;
        }//try
        catch (SQLException e) {
            LOG.error("Erro ao carregar os custos do usuarioId: {}", usuarioId, e);
            throw new RuntimeException("Erro ao carregar custos do banco de dados", e);
        }//catch
    }//buscarPorUsuarioIdCustos

}//CustosDAO
