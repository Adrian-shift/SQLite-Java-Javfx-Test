package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {
        // String de conexão JDBC. O arquivo 'teste.db' será criado na raiz do projeto.
        String url = "jdbc:sqlite:teste.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            System.out.println("SQLite conectado!");

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS pessoas (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL
                    )
                    """);

            statement.execute(
                    "INSERT INTO pessoas (nome) VALUES ('João')"
            );

            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, nome FROM pessoas"
            );

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                System.out.println(id + " - " + nome);
            }

            System.out.println("Banco funcionando!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}