package com.farmalabfx.farmalabfx.models;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
    protected int id;
    private String nome;
    private String telefone;

    public Pessoa(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Pessoa(String nome, String telefone) {
        this.id = -1;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
