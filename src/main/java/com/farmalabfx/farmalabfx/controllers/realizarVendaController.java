package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import com.farmalabfx.farmalabfx.models.Cliente;
import com.farmalabfx.farmalabfx.models.Fornecedor;
import com.farmalabfx.farmalabfx.models.Medicamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class realizarVendaController {
    @FXML
    private TextField txtQuantidade;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<Medicamento> cbMedicamento;

    @FXML
    private Button btnVenda; // Alterado para btnFornecedor
    @FXML
    private Button btnCadastrar;

    private Connection conn;
    private int idFornecedorAtual; // ID do fornecedor que está sendo editado

    private ObservableList<Cliente> clienteList = FXCollections.observableArrayList();
    private ObservableList<Medicamento> medicamentoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            conn = DBConnection.getConnection(); // Conexão com o banco de dados
            clienteList = FXCollections.observableArrayList(Cliente.all());
            cbCliente.setItems(clienteList);

            medicamentoList = FXCollections.observableArrayList(Medicamento.all());
            cbMedicamento.setItems(medicamentoList);
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
    public void CadastrarVenda(ActionEvent event) { // Alterado para CadastrarFornecedor
        int quantidade = Integer.parseInt(txtQuantidade.getText().trim()); // Alterado para cnpj
        Cliente cliente = cbCliente.getSelectionModel().getSelectedItem();
        Medicamento medicamento = cbMedicamento.getSelectionModel().getSelectedItem();

        // Validação básica
        if (quantidade == 0 || cliente == null) { // Alterado para cnpj
            showAlert(Alert.AlertType.WARNING, "Atenção", "Preencha todos os campos!");
            return;
        }

        String sql = "INSERT INTO vendas (id_cliente, id_medicamento, quantidade, preco_total, data) VALUES (?, ?, ?, ?, now())";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cliente.getId());
            pstmt.setInt(2, medicamento.getId());
            pstmt.setInt(3, quantidade); // Alterado para cnpj
            pstmt.setFloat(4, (float) (medicamento.getPreco()*quantidade));
            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Venda cadastrado com sucesso!"); // Alterado para fornecedor

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
        carregaTela("/com/farmalabfx/farmalabfx/vendas.fxml");
    }

    // Método genérico para carregar telas
    private void carregaTela(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnVenda.getScene().getWindow(); // Usa qualquer botão da tela para obter a janela
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
        carregaTela("/com/farmalabfx/farmalabfx/vendas.fxml"); // Retorna para a tela de fornecedores
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
