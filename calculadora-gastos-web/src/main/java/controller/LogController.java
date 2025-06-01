package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.LogDAO;
import modelo.Log;
import modelo.Usuario;

@WebServlet("/logs")
public class LogController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LogDAO logDAO;

    @Override
    public void init() throws ServletException {
        logDAO = new LogDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("listar".equals(action)) {
            listarLogs(request, response);
        } else {
            listarLogs(request, response);
        }
    }

    private void listarLogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Log> logs = logDAO.listarTodos();
            request.setAttribute("logs", logs);
            request.getRequestDispatcher("Log/LogList.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao listar logs.");
            request.getRequestDispatcher("Log/LogList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}