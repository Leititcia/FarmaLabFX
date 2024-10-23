package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import com.farmalabfx.farmalabfx.models.Fornecedor;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cadastroMedicamentoController {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtQuantidade; // Alterado para txtCnpj
    @FXML
    private ComboBox<Fornecedor> cbFornecedor;

    @FXML
    private Button btnMedicamento; // Alterado para btnFornecedor
    @FXML
    private Button btnCadastrar;

    private Connection conn;
    private int idFornecedorAtual; // ID do fornecedor que está sendo editado

    private ObservableList<Fornecedor> fornecedorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            conn = DBConnection.getConnection(); // Conexão com o banco de dados
            fornecedorList = FXCollections.observableArrayList(Fornecedor.all());
            cbFornecedor.setItems(fornecedorList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Conexão", "Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }

    @FXML
    public void clientePage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/cliente.fxml", "Erro ao carregar a página");
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
    public void CadastrarMedicamento(ActionEvent event) { // Alterado para CadastrarFornecedor
        String nome = txtNome.getText().trim();
        float preco = Float.parseFloat(txtPreco.getText().trim());
        int quantidade = Integer.parseInt(txtQuantidade.getText().trim()); // Alterado para cnpj
        Fornecedor fornecedor = cbFornecedor.getSelectionModel().getSelectedItem();

        // Validação básica
        if (nome.isEmpty() || preco == 0 || quantidade == 0 || fornecedor == null) { // Alterado para cnpj
            showAlert(Alert.AlertType.WARNING, "Atenção", "Preencha todos os campos!");
            return;
        }

        String sql = "INSERT INTO medicamentos (nome, preco, quantidade, id_fornecedor) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setFloat(2, preco);
            pstmt.setInt(3, quantidade); // Alterado para cnpj
            pstmt.setInt(4, fornecedor.getId()); // Alterado para cnpj
            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Medicamento cadastrado com sucesso!"); // Alterado para fornecedor

            // Limpar os campos após o cadastro
            limparCampos();

            // Retornar à tela de fornecedores
            voltar(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Cadastrar", "Não foi possível cadastrar o fornecedor: " + e.getMessage()); // Alterado para fornecedor
        }
    }

    // Método para carregar a tela de fornecedores
    @FXML
    public void fornecedorPage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/fornecedor.fxml");
    }

    // Método genérico para carregar telas
    private void carregaTela(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnCadastrar.getScene().getWindow(); // Usa qualquer botão da tela para obter a janela
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao carregar", "Não foi possível carregar a tela: " + e.getMessage());
        }
    }

    // Método para limpar os campos após o cadastro
    private void limparCampos() {
//        txtNome.clear();
//        txtTelefone.clear();
//        txtCnpj.clear(); // Alterado para txtCnpj
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Stage stage = (Stage) btnCadastrar.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the application window
    }

    // Método para voltar à tela anterior
    @FXML
    public void voltar(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/medicamento.fxml"); // Retorna para a tela de fornecedores
    }

    // Método para mostrar alertas
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}
