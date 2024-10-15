package com.farmalabfx.farmalabfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

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

    @FXML
    public void initialize() {
        btnLogin.setOnAction(event -> {
            System.out.println("Botão de login foi clicado!");
            carregaHome();
        });
    }

    private void carregaHome() {
        try {
            // Carrega o layout da tela home
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/home.fxml"));
            Scene homeScene = new Scene(homeRoot, 800, 500);

            // Altera a cena da janela
            Stage window = (Stage) btnLogin.getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela inicial.");
        }
    }
}
