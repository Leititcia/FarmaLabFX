package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField senhaField;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnSair;

    // Método para logar o usuário
    @FXML
    public void logarUsuario(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Email ou senha não podem estar vazios!");
            return;
        }

        // Verificar credenciais no banco de dados
        if (verificarCredenciais(email, senha)) {
            mostrarAlerta("Sucesso", "Login realizado com sucesso!");
            carregaHome();
        } else {
            mostrarAlerta("Erro", "Credenciais inválidas. Tente novamente.");
        }
    }

    // Método para carregar a tela de cadastro
    @FXML
    public void cadastrarUsuario(ActionEvent event) {
        carregaCadastro();
    }

    // Método para sair do sistema
    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }

    // Método para carregar a tela Home
    private void carregaHome() {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/home.fxml"));
            Scene homeScene = new Scene(homeRoot, 800, 500);

            Stage window = (Stage) btnLogin.getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar a tela de cadastro
    private void carregaCadastro() {
        try {
            Parent cadastroRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/CadastroUsuario.fxml"));
            Scene cadastroScene = new Scene(cadastroRoot, 800, 500);

            Stage window = (Stage) btnCadastrar.getScene().getWindow();
            window.setScene(cadastroScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar as credenciais do usuário no banco de dados
    private boolean verificarCredenciais(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Se houver resultados, as credenciais estão corretas
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para exibir alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
