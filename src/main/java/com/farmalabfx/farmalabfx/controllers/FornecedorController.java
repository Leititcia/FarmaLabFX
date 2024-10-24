package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Fornecedor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class FornecedorController {

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnsairDoSistema;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCnpj;

    @FXML
    private TableView<Fornecedor> tableViewFornecedores;

    @FXML
    private TableColumn<Fornecedor, Integer> colId;

    @FXML
    private TableColumn<Fornecedor, String> colNome;

    @FXML
    private TableColumn<Fornecedor, String> colTelefone;

    @FXML
    private TableColumn<Fornecedor, String> colCnpj;

    private ObservableList<Fornecedor> fornecedorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));

        // Carrega os fornecedores do banco de dados ao inicializar
        loadFornecedoresFromDatabase();
    }

    // Método para carregar os fornecedores do banco de dados
    @FXML
    private void loadFornecedoresFromDatabase() {
        fornecedorList.clear(); // Limpa a lista antes de adicionar novos fornecedores

        String sql = "SELECT * FROM fornecedores";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cnpj = rs.getString("cnpj");

                // Adiciona o fornecedor à lista
                fornecedorList.add(new Fornecedor(id, nome, telefone, cnpj));
            }

            // Adiciona a lista de fornecedores na TableView
            tableViewFornecedores.setItems(fornecedorList);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar fornecedores");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
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
    public void TelacadastrarFornecedor(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/CadastroFornecedor.fxml", "Erro ao carregar a página de cadastro");
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

    // Método para remover fornecedor
    @FXML
    public void removerFornecedor(ActionEvent event) {
        Fornecedor fornecedor = tableViewFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedor == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção inválida");
            alert.setHeaderText("Nenhum fornecedor selecionado.");
            alert.setContentText("Por favor, selecione um fornecedor para remover.");
            alert.showAndWait();
            return;
        }

        // Confirmação antes de remover
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmação de Remoção");
        confirmAlert.setHeaderText("Você realmente deseja remover o fornecedor: " + fornecedor.getNome() + "?");
        confirmAlert.setContentText("Essa ação não pode ser desfeita.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remover o fornecedor do banco de dados
                String sql = "DELETE FROM fornecedores WHERE id = ?";
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "abacate");
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, fornecedor.getId());
                    pstmt.executeUpdate();

                    // Remover o fornecedor da lista e atualizar a TableView
                    fornecedorList.remove(fornecedor);
                    tableViewFornecedores.setItems(fornecedorList);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Fornecedor Removido");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Fornecedor removido com sucesso.");
                    successAlert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro ao remover fornecedor");
                    alert.setHeaderText("Erro de Conexão");
                    alert.setContentText("Erro: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    public void abrirTelaEditarFornecedor() {
        Fornecedor fornecedorSelecionado = tableViewFornecedores.getSelectionModel().getSelectedItem(); // Pegando o fornecedor selecionado
        if (fornecedorSelecionado == null) {
            // Mostrar um alerta caso nenhum fornecedor tenha sido selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum fornecedor selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um fornecedor para editar.");
            alert.showAndWait();
            return;
        }

        try {
            // Carregar a tela de cadastro, que será reutilizada para edição
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/farmalabfx/farmalabfx/CadastroFornecedor.fxml"));
            Parent root = loader.load();

            // Obter o controller da tela de cadastro para passar os dados do fornecedor
            CadastroFornecedorController controller = loader.getController();
            controller.inicializarCamposComFornecedor(fornecedorSelecionado); // Método para preencher os campos com o fornecedor selecionado

            // Substituir a tela atual
            Stage stage = (Stage) tableViewFornecedores.getScene().getWindow(); // Obtém a janela atual
            stage.setScene(new Scene(root)); // Substitui a cena pela nova
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
