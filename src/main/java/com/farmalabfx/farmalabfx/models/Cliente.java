package com.farmalabfx.farmalabfx.models;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cliente extends Pessoa {
    private String cpf;


    public Cliente(int id, String nome, String telefone, String cpf) {
        super(id, nome, telefone);
        this.cpf = cpf;
    }

    public Cliente(String nome, String telefone, String cpf) {
        super(nome, telefone);
        this.cpf = cpf;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static ArrayList<Cliente> all(){
        ArrayList<Cliente> ClienteList = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");

                // Adiciona o fornecedor à lista
                ClienteList.add(new Cliente(id, nome, telefone, cpf));
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

        return ClienteList;
    }

    public static Cliente get(int id_search){
        String sql = String.format("SELECT * FROM clientes where id = %d",id_search);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");

                // Adiciona o fornecedor à lista
                return new Cliente(id, nome, telefone, cpf);
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
        return String.format("%s - (%s)",getNome(), getCpf());
    }

}