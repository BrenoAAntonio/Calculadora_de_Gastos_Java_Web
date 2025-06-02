# Calculadora de Gastos - Java Web

Aplicação Java Web para controle de despesas pessoais, com autenticação de usuários, categorização de gastos e geração de relatórios. O projeto segue a arquitetura MVC e possui uma interface web responsiva, conectando-se a um banco de dados MySQL.

---

## Tecnologias Utilizadas

- Java (Servlets / JSP)
- HTML5, CSS3 e JavaScript
- MySQL
- JDBC
- MVC (Model-View-Controller)

---

## Funcionalidades

- Cadastro e login de usuários
- Registro de despesas com categoria e valor
- Visualização e filtragem de despesas
- Logs de ações realizadas pelos usuários
- Configurações personalizadas por usuário

---

## Estrutura do Banco de Dados (MySQL)

```sql
CREATE DATABASE IF NOT EXISTS calculadora_gastos;

USE calculadora_gastos;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE despesas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    categoria_id INT NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_despesa DATE NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    acao VARCHAR(100) NOT NULL,
    detalhes TEXT,
    data_log TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE configuracoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    chave VARCHAR(50) NOT NULL,
    valor VARCHAR(255),
    UNIQUE KEY usuario_chave (usuario_id, chave),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
