package com.farmalabfx.farmalabfx.controllers;

import com.farmalabfx.farmalabfx.models.Cliente;
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

public class clienteController {

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnsairDoSistema;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCpf;

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
        // Configuração das colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        // Carrega os clientes do banco de dados ao inicializar
        loadClientesFromDatabase();
    }

    // Método para carregar os clientes do banco de dados
    @FXML
    private void loadClientesFromDatabase() {
        clienteList.clear(); // Limpa a lista antes de adicionar novos clientes

        String sql = "SELECT * FROM clientes";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");

                // Adiciona o cliente à lista
                clienteList.add(new Cliente(id, nome, telefone, cpf));
            }

            // Adiciona a lista de clientes na TableView
            tableViewClientes.setItems(clienteList);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar clientes");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void clientePage(ActionEvent event) {
        carregarTela(event, "com/farmalabfx/farmalabfx/CadastroUsuario.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void medicamentoPage(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/medicamento.fxml", "Erro ao carregar a página");
    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Stage stage = (Stage) btnsairDoSistema.getScene().getWindow();
        stage.close(); // Fecha a aplicação
    }


    @FXML
    public void TelacadastrarCliente(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/CadastroCliente.fxml", "Erro ao carregar a página de cadastro");
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

    // Método para remover cliente
    @FXML
    public void removerCliente(ActionEvent event) {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção inválida");
            alert.setHeaderText("Nenhum cliente selecionado.");
            alert.setContentText("Por favor, selecione um cliente para remover.");
            alert.showAndWait();
            return;
        }

        // Confirmação antes de remover
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmação de Remoção");
        confirmAlert.setHeaderText("Você realmente deseja remover o cliente: " + cliente.getNome() + "?");
        confirmAlert.setContentText("Essa ação não pode ser desfeita.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remover o cliente do banco de dados
                String sql = "DELETE FROM clientes WHERE id = ?";
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, cliente.getId());
                    pstmt.executeUpdate();

                    // Remover o cliente da lista e atualizar a TableView
                    clienteList.remove(cliente);
                    tableViewClientes.setItems(clienteList);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Cliente Removido");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Cliente removido com sucesso.");
                    successAlert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro ao remover cliente");
                    alert.setHeaderText("Erro de Conexão");
                    alert.setContentText("Erro: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }


    @FXML
    public void abrirTelaEditarCliente() {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem(); // Pegando o cliente selecionado
        if (clienteSelecionado == null) {
            // Mostrar um alerta caso nenhum cliente tenha sido selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum cliente selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um cliente para editar.");
            alert.showAndWait();
            return;
        }

        try {
            // Carregar a tela de cadastro, que será reutilizada para edição
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/farmalabfx/farmalabfx/CadastroCliente.fxml"));
            Parent root = loader.load();

            // Obter o controller da tela de cadastro para passar os dados do cliente
            CadastroClienteController controller = loader.getController();
            controller.inicializarCamposComCliente(clienteSelecionado); // Método para preencher os campos com o cliente selecionado

            // Substituir a tela atual
            Stage stage = (Stage) tableViewClientes.getScene().getWindow(); // Obtém a janela atual
            stage.setScene(new Scene(root)); // Substitui a cena pela nova
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
