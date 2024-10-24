package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Fornecedor;
import com.farmalabfx.farmalabfx.models.Medicamento;
import com.farmalabfx.farmalabfx.models.Venda;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class vendaController {

    @FXML
    private Button btnSair;

    @FXML
    private TableView<Venda> tableViewVendas;

    @FXML
    private TableColumn<Venda, String> colCliente;

    @FXML
    private TableColumn<Venda, String> colMedicamento;

    @FXML
    private TableColumn<Venda, Integer> colQuantidade;//colFornecedor

    @FXML
    private TableColumn<Venda, String> colData;

    private ObservableList<Venda> vendaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        //colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colMedicamento.setCellValueFactory(new PropertyValueFactory<>("medicamento"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Carrega os clientes do banco de dados ao inicializar
        loadVendaFromDatabase();
    }

    @FXML
    private void loadVendaFromDatabase() {
        vendaList.clear(); // Limpa a lista antes de adicionar novos clientes

        vendaList = FXCollections.observableArrayList(Venda.all());

        tableViewVendas.setItems(vendaList);
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
    public void TelaRealizarVenda(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/realizarVenda.fxml", "Erro ao carregar a página de cadastro");
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
