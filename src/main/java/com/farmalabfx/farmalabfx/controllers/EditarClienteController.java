package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarClienteController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCpf;

    @FXML
    private Button btnSalvar;  // Botão de salvar as alterações

    @FXML
    private Button btnVoltar;  // Botão de voltar para a página anterior

    @FXML
    private Label farmaLab;    // Label com o nome FarmaLab

    private Cliente cliente;

    // Método para receber os dados do cliente a serem editados
    public void setClienteDados(Cliente cliente) {
        this.cliente = cliente;
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtCpf.setText(cliente.getCpf());
    }

    // Método para salvar as alterações do cliente
    @FXML
    public void salvarCliente() {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String cpf = txtCpf.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || cpf.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo vazio");
            alert.setHeaderText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE clientes SET nome = ?, telefone = ?, cpf = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cpf);
            pstmt.setInt(4, cliente.getId());

            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cliente Editado");
            alert.setHeaderText("Sucesso");
            alert.setContentText("Cliente editado com sucesso.");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao editar cliente");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // Método para voltar à página anterior
    @FXML
    public void voltar() {
        // Aqui deve ser implementada a lógica para voltar à tela anterior
        // Pode ser uma navegação para outra página
        System.out.println("Voltando para a página anterior...");
    }
}
