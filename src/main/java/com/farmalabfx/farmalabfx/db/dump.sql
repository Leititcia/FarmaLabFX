CREATE DATABASE farmalab;
USE farmalab;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(15),
    cpf VARCHAR(11) NOT NULL UNIQUE
);
CREATE TABLE fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(15),
    cnpj VARCHAR(14) NOT NULL UNIQUE
);
CREATE TABLE medicamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    id_fornecedor INT,
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id) ON DELETE SET NULL
);
CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_medicamento INT,
    quantidade INT NOT NULL,
    data DATE NOT NULL,
    preco_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_medicamento) REFERENCES medicamentos(id) ON DELETE CASCADE
);
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);