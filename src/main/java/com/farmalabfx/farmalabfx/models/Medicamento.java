package com.farmalabfx.farmalabfx.models;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class Medicamento {
    private int id;
    private String nome;
    private Double preco;
    private int quantidade;
    private Fornecedor fornecedor;


    public Medicamento(int id, String nome, double preco, int quantidade, Fornecedor fornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public Medicamento(String nome, double preco, int quantidade, Fornecedor fornecedor) {
        this.nome = nome;
        this.id = -1; // Você pode querer mudar a lógica para o id
        this.preco = preco; // Converte string para double
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getPrecoFormatado() {
        return String.format("R$ %.2f", getPreco());
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public static ArrayList<Medicamento> all(){
        ArrayList<Medicamento> medicamentoList = new ArrayList<>();

        String sql = "SELECT * FROM medicamentos";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                float preco = rs.getFloat("preco");
                int quantidade = rs.getInt("quantidade");
                int id_fornecedor = rs.getInt("id_fornecedor");

                Fornecedor fornecedor = Fornecedor.get(id_fornecedor);

                // Adiciona o cliente à lista
                medicamentoList.add(new Medicamento(id, nome, preco, quantidade, fornecedor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar clientes");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }

        return medicamentoList;
    }

    public static Medicamento get(int id_search){
        String sql = String.format("SELECT * FROM medicamentos where id = %d",id_search);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                float preco = rs.getFloat("preco");
                int quantidade = rs.getInt("quantidade");
                int id_fornecedor = rs.getInt("id_fornecedor");

                Fornecedor fornecedor = Fornecedor.get(id_fornecedor);

                // Adiciona o fornecedor à lista
                return new Medicamento(id, nome, preco, quantidade, fornecedor);
            }

            // Adiciona a lista de fornecedores na TableView
            // tableViewFornecedores.setItems(fornecedorList);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar fornecedores");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("%s - (%s)",getNome(), getPrecoFormatado());
    }
}