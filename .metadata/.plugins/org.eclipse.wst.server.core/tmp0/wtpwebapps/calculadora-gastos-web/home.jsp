<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% if (session.getAttribute("usuarioLogado") == null) {
    response.sendRedirect(request.getContextPath() + "/Login/login.jsp");
} %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculadora de Gastos</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="body-content">
        <div class="container main-content">
            <div class="glass-container">
                <h1>Bem-vindo(a), <% if (session.getAttribute("usuarioLogado") != null) { %>
                    <%= ((modelo.Usuario) session.getAttribute("usuarioLogado")).getNome() %>!
                <% } else { %>
                    Visitante!
                <% } %>
                </h1>

                <div class="dashboard-info">
                    <div class="info-card">
                        <h2>Gasto nos Últimos 30 Dias</h2>
                        <p>R$ <fmt:formatNumber value="${totalGasto30Dias}" pattern="#,##0.00"/></p>
                    </div>
                    <div class="info-card">
                        <h2>Total por Categoria</h2>
                        <ul>
                            <c:forEach var="gasto" items="${gastosPorCategoria}">
                                <li>${gasto.key}: R$ <fmt:formatNumber value="${gasto.value}" pattern="#,##0.00"/></li>
                            </c:forEach>
                            <c:if test="${empty gastosPorCategoria}">
                                <li>Nenhuma despesa por categoria ainda.</li>
                            </c:if>
                        </ul>
                    </div>
                </div>

                <div class="recent-expenses glass-container">
                    <h2>Despesas Recentes</h2>
                    <c:if test="${not empty despesasRecentes}">
                        <table>
                            <thead>
                                <tr>
                                    <th>Descrição</th>
                                    <th>Valor</th>
                                    <th>Data</th>
                                    <th>Categoria</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="despesa" items="${despesasRecentes}">
                                    <tr>
                                        <td>${despesa.descricao}</td>
                                        <td>R$ <fmt:formatNumber value="${despesa.valor}" pattern="#,##0.00"/></td>
                                        <td>${despesa.dataDespesa}</td>
                                        <td>${despesa.categoria.nome}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty despesasRecentes}">
                        <p>Nenhuma despesa cadastrada recentemente.</p>
                    </c:if>
                </div>
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