package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ConfiguracaoDAO;
import modelo.Configuracao;
import modelo.Usuario;

@WebServlet("/configuracoes-usuario")
public class ConfiguracaoUsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConfiguracaoDAO configuracaoDAO;

    @Override
    public void init() throws ServletException {
        configuracaoDAO = new ConfiguracaoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Configuracao rendaMensalConfig = configuracaoDAO.buscarPorChave(usuarioLogado.getId(), "renda_mensal");
            request.setAttribute("rendaMensal", rendaMensalConfig != null ? rendaMensalConfig.getValor() : "");

            request.getRequestDispatcher("Usuario/configuracoes_usuario.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar configurações.");
            request.getRequestDispatcher("Usuario/configuracoes_usuario.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String rendaMensal = request.getParameter("renda_mensal");

        try {
            Configuracao rendaConfig = new Configuracao();
            rendaConfig.setUsuario(usuarioLogado);
            rendaConfig.setChave("renda_mensal");
            rendaConfig.setValor(rendaMensal);
            configuracaoDAO.salvar(rendaConfig);

            request.setAttribute("mensagem", "Configurações salvas com sucesso!");
            doGet(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao salvar configurações.");
            request.getRequestDispatcher("Usuario/configuracoes_usuario.jsp").forward(request, response);
        }
    }
}