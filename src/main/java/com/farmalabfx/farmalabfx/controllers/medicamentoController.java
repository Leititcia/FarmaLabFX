package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Cliente;
import com.farmalabfx.farmalabfx.models.Fornecedor;
import com.farmalabfx.farmalabfx.models.Medicamento;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class medicamentoController {

    @FXML
    private TableView<Medicamento> tableViewClientes;

    @FXML
    private TableColumn<Medicamento, Integer> colId;

    @FXML
    private TableColumn<Medicamento, String> colNome;

    @FXML
    private TableColumn<Medicamento, Float> colPreco;

    @FXML
    private TableColumn<Medicamento, Integer> colQuantidade;//colFornecedor

    @FXML
    private TableColumn<Medicamento, String> colFornecedor;

    private ObservableList<Medicamento> medicamentoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        //colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoFormatado"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));

        // Carrega os clientes do banco de dados ao inicializar
        loadMedicamentosFromDatabase();
    }

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

    @FXML
    private void loadMedicamentosFromDatabase() {
        medicamentoList.clear(); // Limpa a lista antes de adicionar novos clientes

        medicamentoList = FXCollections.observableArrayList(Medicamento.all());

        tableViewClientes.setItems(medicamentoList);
    }

}
