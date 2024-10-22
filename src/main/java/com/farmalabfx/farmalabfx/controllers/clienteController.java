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
import javafx.util.Callback;
import javafx.scene.layout.HBox;

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
    private Button btnsairDoSistema;

    @FXML
    private TextField txtNome;

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

        // Adicione os novos campos de texto para o cadastro
        @FXML
        private TextField txtTelefone;

        @FXML
        private TextField txtCpf;

        // Método para cadastrar cliente
        @FXML
        public void cadastrarCliente(ActionEvent event) {
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

            String sql = "INSERT INTO clientes (nome, telefone, cpf) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nome);
                pstmt.setString(2, telefone);
                pstmt.setString(3, cpf);
                pstmt.executeUpdate();

                // Limpa os campos após cadastrar
                txtNome.clear();
                txtTelefone.clear();
                txtCpf.clear();

                // Atualiza a tabela
                loadClientesFromDatabase();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente Cadastrado");
                alert.setHeaderText("Sucesso");
                alert.setContentText("Cliente cadastrado com sucesso.");
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao cadastrar cliente");
                alert.setHeaderText("Erro de Conexão");
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }

        // Método para editar cliente
        @FXML
        public void editarCliente(ActionEvent event) {
            Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seleção inválida");
                alert.setHeaderText("Nenhum cliente selecionado.");
                alert.showAndWait();
                return;
            }

            // Coleta dados do cliente selecionado
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
                pstmt.setInt(4, cliente.getId()); // ID do cliente selecionado

                pstmt.executeUpdate();

                // Atualiza a tabela
                loadClientesFromDatabase();

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

        // Método para remover cliente
        @FXML
        public void removerCliente(ActionEvent event) {
            Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seleção inválida");
                alert.setHeaderText("Nenhum cliente selecionado.");
                alert.showAndWait();
                return;
            }

            String sql = "DELETE FROM clientes WHERE id = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmalab", "root", "5002");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, cliente.getId()); // ID do cliente selecionado
                pstmt.executeUpdate();

                // Atualiza a tabela
                loadClientesFromDatabase();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente Removido");
                alert.setHeaderText("Sucesso");
                alert.setContentText("Cliente removido com sucesso.");
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao remover cliente");
                alert.setHeaderText("Erro de Conexão");
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }
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

    // Carrega os clientes do banco de dados
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
    public void voltar(ActionEvent event) {
        carregarTela(event, "/com/farmalabfx/farmalabfx/views/cliente.fxml", "Erro ao voltar");
    }

    @FXML
    public void clientePage(ActionEvent event) {
        carregarTela(event, "com/farmalabfx/farmalabfx/CadastroUsuario.fxml", "Erro ao carregar a página");
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
    @FXML
    public void abrirTelaEditarCliente(ActionEvent event) {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção inválida");
            alert.setHeaderText("Nenhum cliente selecionado.");
            alert.showAndWait();
            return;
        }

        try {
            // Carrega o FXML da tela de edição
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/com/farmalabfx/farmalabfx/editarCliente.fxml"));
            Parent root = loader.load();

            // Obtém o controller da tela de edição
            EditarClienteController editarController = loader.getController();

            // Passa os dados do cliente selecionado para a tela de edição
            editarController.setClienteDados(clienteSelecionado);

            // Abre a nova janela
            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar a tela de edição.");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }

}
