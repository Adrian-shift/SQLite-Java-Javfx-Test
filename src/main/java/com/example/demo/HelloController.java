package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    @FXML
    private TextField nomeTextField;

    @FXML
    private ListView<String> pessoasListView;

    private String dbUrl;

    @FXML
    public void initialize() {

        String userHome = System.getProperty("user.home");


        File pastaApp = new File(userHome, "MeuTesteApp");


        if (!pastaApp.exists()) {
            pastaApp.mkdirs();
        }
        dbUrl = "jdbc:sqlite:" + pastaApp.getAbsolutePath() + File.separator + "teste.db";

        criarTabelaSeNaoExistir();
        carregarPessoas();
    }

    @FXML
    protected void onSalvarClick() {
        String nome = nomeTextField.getText();
        if (nome != null && !nome.trim().isEmpty()) {
            salvarNoBanco(nome);
            nomeTextField.clear();
            carregarPessoas();
        }
    }

    private void criarTabelaSeNaoExistir() {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS pessoas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarPessoas() {
        ObservableList<String> listaPessoas = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nome FROM pessoas")) {

            while (rs.next()) {
                listaPessoas.add(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pessoasListView.setItems(listaPessoas);
    }

    private void salvarNoBanco(String nome) {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO pessoas (nome) VALUES ('" + nome + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}