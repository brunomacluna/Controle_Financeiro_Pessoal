package br.com.brunoluna.controlefinanceiro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/controle_financeiro";
    private static final String USUARIO = "postgres";


    public static Connection getConnection() throws SQLException {
        String senha = System.getenv("DB_PASSWORD_POSTGRES");
        if (senha == null) {
            throw new IllegalStateException("Variável de ambiente DB_PASSWORD_POSTGRES não configurada");
        }
        return DriverManager.getConnection(URL, USUARIO, senha);
    }//getConnection

}//ConexaoFactory
