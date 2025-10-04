package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/controle_financeiro";
    private static final String USUARIO = "postgresql";
    private static final String SENHA = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }//getConnection

}//ConexaoFactory
