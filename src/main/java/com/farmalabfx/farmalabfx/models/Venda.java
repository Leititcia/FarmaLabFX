package com.farmalabfx.farmalabfx.models;

import com.farmalabfx.farmalabfx.db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private Medicamento medicamento;
    private int quantidade;
    private Date data;
    private double precoTotal;

    private static List<Venda> vendas = new ArrayList<>();

    public Venda(int id, Cliente cliente, Medicamento medicamento, int quantidade, Date data) {
        this.id = id;
        this.cliente = cliente;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.data = data;
        this.precoTotal = medicamento.getPreco() * quantidade;
        vendas.add(this);
    }

    public Venda( Cliente cliente, Medicamento medicamento, int quantidade, Date data) {
        this.id = -1;
        this.cliente = cliente;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.data = data;
        this.precoTotal = medicamento.getPreco() * quantidade;
        vendas.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) { // Corrigido para void
        this.medicamento = medicamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) { // Corrigido para void
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public static ArrayList<Venda> all(){
        ArrayList<Venda> vendaList = new ArrayList<>();

        String sql = "SELECT * FROM vendas";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int id_cliente = rs.getInt("id_cliente");
                int id_medicamento = rs.getInt("id_medicamento");
                int quantidade = rs.getInt("quantidade");
                Date data = rs.getDate("data");

                Cliente cliente = Cliente.get(id_cliente);
                Medicamento medicamento = Medicamento.get(id_medicamento);

                // Adiciona o cliente à lista
                vendaList.add(new Venda(id, cliente, medicamento, quantidade, data));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar clientes");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }

        return vendaList;
    }
}