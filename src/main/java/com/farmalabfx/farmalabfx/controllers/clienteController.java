package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Cliente;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

public class clienteController {

    @FXML
    public void clientePage(ActionEvent event) {

    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }

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
    @FXML
    private TableColumn<Cliente, Void> colunaAcoes;

    private ObservableList<Cliente> clienteList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        clienteList.add(new Cliente(1, "Maria", "99999-9999", "123.456.789-00"));
        clienteList.add(new Cliente(2, "José", "88888-8888", "987.654.321-00"));
        tableViewClientes.setItems(clienteList);

        adicionaBtnTable();
    }

    //adiciona botoes a tebela
    private void adicionaBtnTable() {
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                final TableCell<Cliente, Void> cell = new TableCell<>() {
                    private final Button btnVisualizar = new Button("Visualizar");
                    private final Button btnEditar = new Button("Editar");

                    {

                        btnVisualizar.setStyle("-fx-background-color: #7E69A3; " +
                                "-fx-text-fill: white; ");

                        btnEditar.setStyle("-fx-background-color: #7E69A3; " +
                                "-fx-text-fill: white; ");

                        btnVisualizar.setOnAction(event -> {
                            Cliente cliente = getTableView().getItems().get(getIndex());
                            System.out.println("Visualizar cliente: " + cliente.getNome());
                            // logica visualizar
                        });

                        btnEditar.setOnAction(event -> {
                            Cliente cliente = getTableView().getItems().get(getIndex());
                            System.out.println("Editar cliente: " + cliente.getNome());
                            // logica edit
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(btnVisualizar, btnEditar);
                            hbox.setSpacing(10);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

        colunaAcoes.setCellFactory(cellFactory);
    }
}
