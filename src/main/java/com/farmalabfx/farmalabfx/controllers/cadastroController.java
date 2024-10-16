package com.farmalabfx.farmalabfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class cadastroController {

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

        // Lógica de cadastro (por exemplo, salvar no banco de dados)
        // Aqui você pode adicionar a lógica para armazenar os dados do usuário

        nomeCadastro.clear();
        emailCadastro.clear();
        senhaCadastro.clear();

        mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!");
        logarUsuario(new ActionEvent());

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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}