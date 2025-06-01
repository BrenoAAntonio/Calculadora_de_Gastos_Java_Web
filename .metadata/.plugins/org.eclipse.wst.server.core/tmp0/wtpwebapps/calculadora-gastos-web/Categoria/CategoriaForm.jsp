<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% if (session.getAttribute("usuarioLogado") == null) {
    response.sendRedirect("../login.jsp");
} %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Categoria</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            background: linear-gradient(135deg, #a7f0fb, #50c878);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .glass-container {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 16px;
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            padding: 30px;
            width: 350px;
            text-align: center;
        }

        h2 {
            color: #fff;
            margin-bottom: 20px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #fff;
            text-align: left;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        }

        input[type="text"],
        textarea {
            width: calc(100% - 22px);
            padding: 10px;
            border: 1px solid rgba(255, 255, 255, 0.3);
            border-radius: 5px;
            box-sizing: border-box;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.2);
            color: #333;
        }

        button {
            background-color: rgba(255, 255, 255, 0.3);
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: rgba(255, 255, 255, 0.4);
        }

        .error-message {
            color: #ff4d4d;
            margin-top: 10px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        }

        .back-link {
            margin-top: 15px;
            font-size: 0.9em;
            color: #eee;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        }

        .back-link a {
            color: #fff;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }

        .body-content {
            display: flex;
            flex-grow: 1;
            justify-content: center;
            align-items: center;
            width: 100%;
        }

        .main-content {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
    <div class="body-content">
        <div class="container main-content">
            <div class="glass-container">
                <h2>Cadastrar Categoria</h2>
                <% if (request.getAttribute("erro") != null) { %>
                    <p class="error-message"><%= request.getAttribute("erro") %></p>
                <% } %>
                <form action="<%= request.getContextPath() %>/categoria?action=cadastrar" method="post">
                    <div class="form-group">
                        <label for="nome">Nome:</label>
                        <input type="text" id="nome" name="nome" required>
                    </div>
                    <div class="form-group">
                        <label for="descricao">Descrição:</label>
                        <textarea id="descricao" name="descricao"></textarea>
                    </div>
                    <button type="submit">Salvar Categoria</button>
                </form>
            </div>
        </div>
        <div class="sidebar">
            <h2>Menu</h2>
            <a href="<%= request.getContextPath() %>/home">Home</a>
            <a href="<%= request.getContextPath() %>/categoria?action=form">Cadastrar Categoria</a>
            <a href="<%= request.getContextPath() %>/despesas?action=form">Cadastrar Despesa</a>
            <a href="<%= request.getContextPath() %>/categoria?action=listar">Listar Categorias</a>
            <a href="<%= request.getContextPath() %>/despesas?action=listar">Listar Despesas</a>
            <a href="<%= request.getContextPath() %>/logs?action=listar">Ver Logs</a>
            <a href="<%= request.getContextPath() %>/configuracoes-usuario">Configurações</a>
            <a href="<%= request.getContextPath() %>/logout">Sair</a>
        </div>
    </div>
</body>
</html>