package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class homeController {

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnMedicamentos;

    @FXML
    private Button btnVendas;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblClientes;

    @FXML
    private Label lblFornecedores;

    @FXML
    private Label lblMedicamentos;

    @FXML
    private Label lblVendas;

    private Connection connection;

    // Método para sair do sistema
    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }

    // Método para carregar a tela de clientes
    @FXML
    public void clientePage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/cliente.fxml");
    }

    // Método para carregar a tela de fornecedores
    @FXML
    public void fornecedorPage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/fornecedor.fxml");
    }

    // Método para carregar a tela de medicamentos
    @FXML
    public void medicamentosPage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/medicamentos.fxml");
    }

    // Método para carregar a tela de vendas
    @FXML
    public void vendasPage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/vendas.fxml");
    }

    // Método genérico para carregar telas
    private void carregaTela(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnCliente.getScene().getWindow(); // Usa qualquer botão da tela para obter a janela
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para inicializar o controller
    @FXML
    public void initialize() throws SQLException {
        // Inicializar a conexão com o banco de dados
        DBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();

        // Atualizar os valores dos contadores
        atualizarContadores();
    }

    // Método para buscar os dados e atualizar os contadores
    private void atualizarContadores() {
        // Atualiza as labels com as contagens dos registros
        lblClientes.setText("Clientes: " + getCount("clientes"));
        lblFornecedores.setText("Fornecedores: " + getCount("fornecedores"));
        lblMedicamentos.setText("Medicamentos: " + getCount("medicamentos"));
        lblVendas.setText("Vendas: " + getCount("vendas"));
    }

    // Método para contar o número de registros em uma tabela específica
    private int getCount(String tabela) {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM " + tabela;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
