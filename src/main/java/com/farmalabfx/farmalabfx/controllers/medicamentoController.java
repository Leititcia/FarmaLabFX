package com.farmalabfx.farmalabfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class medicamentoController {

    // Método para cadastrar um medicamento
    @FXML
    public void TelaCadastrarMedicamento() {
        // Lógica para abrir a tela de cadastro
    }

    // Método para editar um medicamento
    @FXML
    public void TelaEditarMedicamento() {
        // Lógica para abrir a tela de edição
    }

    // Método para excluir um medicamento
    @FXML
    public void ExcluirMedicamento() {
        // Lógica para excluir o medicamento selecionado
    }


    @FXML
    public void TelacadastrarMedicamento(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/cadastroMedicamento.fxml", "Erro ao carregar a página de cadastro");
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
