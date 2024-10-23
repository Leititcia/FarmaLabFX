package com.farmalabfx.farmalabfx.models;

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

}