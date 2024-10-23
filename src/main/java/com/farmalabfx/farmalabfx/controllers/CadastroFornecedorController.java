package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.db.DBConnection;
import com.farmalabfx.farmalabfx.models.Fornecedor; // Alterado para Fornecedor
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroFornecedorController { // Alterado para CadastroFornecedorController

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCnpj; // Alterado para txtCnpj

    @FXML
    private Button btnFornecedor; // Alterado para btnFornecedor
    @FXML
    private Button btnCadastrar;

    private Connection conn;
    private int idFornecedorAtual; // ID do fornecedor que está sendo editado

    @FXML
    public void initialize() {
        try {
            conn = DBConnection.getConnection(); // Conexão com o banco de dados
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro de Conexão", "Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }

    @FXML
    public void CadastrarFornecedor(ActionEvent event) { // Alterado para CadastrarFornecedor
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String cnpj = txtCnpj.getText().trim(); // Alterado para cnpj

        // Validação básica
        if (nome.isEmpty() || telefone.isEmpty() || cnpj.isEmpty()) { // Alterado para cnpj
            showAlert(AlertType.WARNING, "Atenção", "Preencha todos os campos!");
            return;
        }

        // Verifica se o CNPJ já existe
        if (cnpjJaExiste(cnpj, -1)) { // Alterado para cnpj
            showAlert(AlertType.WARNING, "Atenção", "Já existe um fornecedor cadastrado com esse CNPJ."); // Alterado para fornecedor
            return; // Impede o cadastro se houver duplicidade
        }

        String sql = "INSERT INTO fornecedores (nome, telefone, cnpj) VALUES (?, ?, ?)"; // Alterado para cnpj

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setString(3, cnpj); // Alterado para cnpj
            pstmt.executeUpdate();
            showAlert(AlertType.INFORMATION, "Sucesso", "Fornecedor cadastrado com sucesso!"); // Alterado para fornecedor

            // Limpar os campos após o cadastro
            limparCampos();

            // Retornar à tela de fornecedores
            voltar(event);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro ao Cadastrar", "Não foi possível cadastrar o fornecedor: " + e.getMessage()); // Alterado para fornecedor
        }
    }

    public void inicializarCamposComFornecedor(Fornecedor fornecedor) { // Alterado para Fornecedor
        if (fornecedor != null) {
            txtNome.setText(fornecedor.getNome()); // Preencher o campo nome
            txtCnpj.setText(fornecedor.getCnpj()); // Alterado para txtCnpj
            txtTelefone.setText(fornecedor.getTelefone()); // Preencher o campo telefone
            this.idFornecedorAtual = fornecedor.getId(); // Armazenar o ID do fornecedor atual
        }
    }

    @FXML
    public void salvarFornecedor() { // Alterado para salvarFornecedor
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String cnpj = txtCnpj.getText().trim(); // Alterado para cnpj

        // Validação básica
        if (nome.isEmpty() || telefone.isEmpty() || cnpj.isEmpty()) { // Alterado para cnpj
            showAlert(AlertType.WARNING, "Atenção", "Preencha todos os campos!");
            return;
        }

        // Verifica se o CNPJ já existe antes de atualizar
        if (cnpjJaExiste(cnpj, idFornecedorAtual)) { // Alterado para idFornecedorAtual
            showAlert(AlertType.WARNING, "Atenção", "Já existe um fornecedor cadastrado com esse CNPJ."); // Alterado para fornecedor
            return; // Impede a atualização se houver duplicidade
        }

        // Correção na criação do Fornecedor
        Fornecedor fornecedorAtualizado = new Fornecedor(idFornecedorAtual, nome, telefone, cnpj); // Alterado para Fornecedor
        atualizarFornecedorNoBanco(fornecedorAtualizado); // Alterado para atualizarFornecedorNoBanco

        // Fechar a tela após salvar
        voltar(null);
    }

    private void atualizarFornecedorNoBanco(Fornecedor fornecedor) { // Alterado para Fornecedor
        String sql = "UPDATE fornecedores SET nome = ?, telefone = ?, cnpj = ? WHERE id = ?"; // Alterado para cnpj

        try (Connection conexao = DBConnection.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, fornecedor.getNome());
            ps.setString(2, fornecedor.getTelefone());
            ps.setString(3, fornecedor.getCnpj()); // Atualiza o CNPJ
            ps.setInt(4, fornecedor.getId()); // Atualizando pelo ID

            // Executa a atualização
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                showAlert(AlertType.INFORMATION, "Sucesso", "Fornecedor atualizado com sucesso!"); // Alterado para fornecedor
            } else {
                showAlert(AlertType.WARNING, "Atenção", "Nenhum fornecedor encontrado com esse ID."); // Alterado para fornecedor
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Erro ao atualizar fornecedor: " + e.getMessage()); // Alterado para fornecedor
        }
    }

    private boolean cnpjJaExiste(String cnpj, int idFornecedor) { // Alterado para idFornecedor
        String sql = "SELECT COUNT(*) FROM fornecedores WHERE cnpj = ? AND id != ?"; // Alterado para cnpj
        try (Connection conexao = DBConnection.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, cnpj); // Alterado para cnpj
            ps.setInt(2, idFornecedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se já existe outro fornecedor com o mesmo CNPJ
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não houver duplicidade
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
            Stage stage = (Stage) btnFornecedor.getScene().getWindow(); // Usa qualquer botão da tela para obter a janela
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
        txtCnpj.clear(); // Alterado para txtCnpj
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Stage stage = (Stage) btnCadastrar.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the application window
    }

    // Método para voltar à tela anterior
    @FXML
    public void voltar(ActionEvent event) {
        carregaTela("/com/farmalabfx/farmalabfx/fornecedor.fxml"); // Retorna para a tela de fornecedores
    }

    // Método para mostrar alertas
    private void showAlert(AlertType type, String title, String message) {
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
