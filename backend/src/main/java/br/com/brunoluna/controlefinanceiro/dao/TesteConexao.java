package br.com.brunoluna.controlefinanceiro.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("Testando conexão com o banco...");

        try {
            Connection conn = ConexaoFactory.getConnection();

            System.out.println("Conexão bem sucedida!");
            System.out.println("Banco: "+ conn.getMetaData().getDatabaseProductName());
            System.out.println("Versão: "+ conn.getMetaData().getDatabaseProductVersion());

            conn.close();
            System.out.println("Conexão fechada corretamente.");
        } catch (SQLException e) {
            System.out.println("ERRO NA CONEXÃO:");
            System.out.println("Mensagem: "+ e.getMessage());
            System.out.println("Código SQL: "+ e.getSQLState());

            if (e.getMessage().contains("password")) {
                System.out.println("Dica: Verifique a senha na variável de ambiente");
            }
            if (e.getMessage().contains("connection refused")) {
                System.out.println("Dica: PostgreSQL não está rodando na porta 5432");
            }
        } catch (IllegalStateException e) {
            System.out.println("ERRO DE CONFiGURAÇÃO:");
            System.out.println(e.getMessage());
            System.out.println("Dica: Configure a variável de ambiente DB_PASSWORD_POSTGRES");
        }
    }//main
}//TesteConexao
