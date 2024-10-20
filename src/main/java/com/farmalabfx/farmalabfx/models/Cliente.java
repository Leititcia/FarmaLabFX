package com.farmalabfx.farmalabfx.models;

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

}
