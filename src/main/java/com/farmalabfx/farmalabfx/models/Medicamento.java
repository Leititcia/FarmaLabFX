package com.farmalabfx.farmalabfx.models;

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


}
