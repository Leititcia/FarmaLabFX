package com.farmalabfx.farmalabfx.models;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class Fornecedor extends Pessoa {
    private String cnpj;


    public Fornecedor(int id, String nome, String telefone, String cnpj) {
        super(id, nome, telefone);
        this.cnpj = cnpj;
    }

    public Fornecedor(String nome, String cnpj, String telefone) {
        super(nome, telefone);
        this.cnpj = cnpj;
    }


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public static ArrayList<Fornecedor> all(){
        ArrayList<Fornecedor> fornecedorList = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores";

        try (Connection conn = DBConnection.getConnection();
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
           // tableViewFornecedores.setItems(fornecedorList);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar fornecedores");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }

        return fornecedorList;
    }

    public static Fornecedor get(int id_search){
        String sql = String.format("SELECT * FROM fornecedores where id = %d",id_search);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cnpj = rs.getString("cnpj");

                // Adiciona o fornecedor à lista
                return new Fornecedor(id, nome, telefone, cnpj);
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
        return String.format("%s - (%s)",getNome(), getCnpj());
    }
}