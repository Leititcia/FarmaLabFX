package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroUsuarioController {

    @FXML
    private TextField nomeCadastro;

    @FXML
    private TextField emailCadastro;

    @FXML
    private TextField senhaCadastro;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSair;

    @FXML
    private Label emailLabel;

    @FXML
    private Label senhaLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private void cadastrarUsuario(ActionEvent event) {
        String nome = nomeCadastro.getText();
        String email = emailCadastro.getText();
        String senha = senhaCadastro.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos devem ser preenchidos!");
            return;
        }

        // Lógica de cadastro no banco de dados
        if (salvarUsuarioNoBanco(nome, email, senha)) {
            mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!");
            logarUsuario(new ActionEvent());
        } else {
            mostrarAlerta("Erro", "Erro ao cadastrar o usuário.");
        }
    }

    private boolean salvarUsuarioNoBanco(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);

            int result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    public void logarUsuario(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/login.fxml"));
            Scene loginScene = new Scene(loginRoot, 800, 500);

            Stage window = (Stage) btnLogin.getScene().getWindow();
            window.setScene(loginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a tela de login.");
        }
    }

    @FXML
    private void sairDoSistema() {
        System.exit(0);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
