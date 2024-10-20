package com.farmalabfx.farmalabfx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Button btnCliente;

    @FXML
    public void clientePage(ActionEvent event) {
        carregaCliente();
    }
    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }

    private void carregaCliente() {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/farmalabfx/farmalabfx/cliente.fxml"));
            Scene homeScene = new Scene(homeRoot, 800, 500);

            Stage window = (Stage) btnCliente.getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
