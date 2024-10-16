package com.farmalabfx.farmalabfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;
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
    public void logarUsuario(ActionEvent event) {
//        String email = emailField.getText();
//        String senha = senhaField.getText();
//
//        if (email.equals("lele@email.com") && senha.equals("123")) {
        carregaHome();
//        } else {
//            System.out.println("Credenciais inválidas");
//        }
    }

    @FXML
    public void cadastrarUsuario(ActionEvent event) {
        carregaCadastro();
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }

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

    private void carregaCadastro() {
        try {
            Parent cadastroRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/cadastro.fxml"));
            Scene cadastroScene = new Scene(cadastroRoot, 800, 500);

            Stage window = (Stage) btnCadastrar.getScene().getWindow();
            window.setScene(cadastroScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
