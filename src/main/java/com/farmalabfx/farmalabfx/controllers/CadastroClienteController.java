package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import com.farmalabfx.farmalabfx.models.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroClienteController {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCpf;

    @FXML
    private Button btnCliente;
    @FXML
    private Button btnCadastrar;

    private Connection conn;

    @FXML
    public void initialize() {
        try {
            conn = DBConnection.getConnection(); // Conexão com o banco de dados
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro de Conexão", "Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }

    @FXML
    public void CadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String cpf = txtCpf.getText().trim();

        // Validação básica
        if (nome.isEmpty() || telefone.isEmpty() || cpf.isEmpty()) {
            showAlert(AlertType.WARNING, "Atenção", "Preencha todos os campos!");
            return;
        }

        String sql = "INSERT INTO clientes (nome, telefone, cpf) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cpf);
            pstmt.executeUpdate();
            showAlert(AlertType.INFORMATION, "Sucesso", "Cliente cadastrado com sucesso!");

            // Limpar os campos após o cadastro
            limparCampos();

            // Retornar à tela de clientes
            voltar(event);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro ao Cadastrar", "Não foi possível cadastrar o cliente: " + e.getMessage());
        }
    }

    // Método para carregar a tela de cliente
    @FXML
    public void clientePage(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/cliente.fxml");
    }

    // Método genérico para carregar telas
    private void carregaTela(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnCliente.getScene().getWindow(); // Usa qualquer botão da tela para obter a janela
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro ao carregar", "Não foi possível carregar a tela: " + e.getMessage());
        }
    }

    // Método para limpar os campos após o cadastro
    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtCpf.clear();
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Stage stage = (Stage) btnCadastrar.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the application window
    }

    // Método para voltar à tela anterior
    @FXML
    public void voltar(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/cliente.fxml"); // Retorna para a tela de clientes
    }

    // Método para mostrar alertas
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
