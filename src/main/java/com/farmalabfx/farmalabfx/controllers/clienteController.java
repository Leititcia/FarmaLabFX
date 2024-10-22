package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Cliente;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class clienteController {

    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, Integer> colId;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colTelefone;
    @FXML
    private TableColumn<Cliente, String> colCpf;

    private ObservableList<Cliente> clienteList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregaCliente();
    }

    private void carregaCliente() {
        // Mapeando as colunas com as propriedades da classe Cliente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        // Adicionar dados de exemplo
        clienteList.add(new Cliente(1, "Maria", "99999-9999", "123.456.789-00"));
        clienteList.add(new Cliente(2, "José", "88888-8888", "987.654.321-00"));
        tableViewClientes.setItems(clienteList);
    }

    @FXML
    public void clientePage(ActionEvent event) {
        // Lógica para ir à página do cliente
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }
}
