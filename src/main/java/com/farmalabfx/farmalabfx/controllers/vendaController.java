package com.farmalabfx.farmalabfx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class vendaController {

    @FXML
    public void clientePage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/cliente.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void medicamentosPage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/medicamento.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void fornecedorPage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/fornecedor.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void vendasPage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/vendas.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }


    // Método auxiliar para carregar telas
    private void carregarTela(ActionEvent event, String resourcePath, String errorMessage) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(resourcePath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(page));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(errorMessage);
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }

}
