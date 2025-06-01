package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import dao.LogDAO;
import modelo.Categoria;
import modelo.Despesa;
import modelo.Log;
import modelo.Usuario;

@WebServlet("/despesas")
public class DespesasController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DespesaDAO despesaDAO;
    private CategoriaDAO categoriaDAO;
    private LogDAO logDAO = new LogDAO();

    @Override
    public void init() throws ServletException {
        despesaDAO = new DespesaDAO();
        categoriaDAO = new CategoriaDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarDespesa(request, response);
        } else if ("editar".equals(action)) {
            editarDespesa(request, response);
        }
    }

    private void cadastrarDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
            String descricao = request.getParameter("descricao");
            BigDecimal valor = new BigDecimal(request.getParameter("valor"));
            Date dataDespesa = Date.valueOf(request.getParameter("dataDespesa"));

            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
            if (categoria == null) {
                request.setAttribute("erro", "Categoria inválida.");
                carregarCategoriasParaFormulario(request, response);
                request.getRequestDispatcher("Despesas/DespesasForm.jsp").forward(request, response);
                return;
            }

            Despesa despesa = new Despesa();
            despesa.setUsuario(usuarioLogado);
            despesa.setCategoria(categoria);
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            despesa.setDataDespesa(dataDespesa);

            despesaDAO.cadastrar(despesa);

            Log log = new Log();
            log.setUsuario(usuarioLogado);
            log.setAcao("Cadastro de Despesa");
            log.setDetalhes("Despesa '" + descricao + "' no valor de " + valor + " cadastrada.");
            logDAO.registrar(log);

            response.sendRedirect(request.getContextPath() + "/despesas?action=listar&cadastroSucesso=true");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", "Dados inválidos para a despesa.");
            carregarCategoriasParaFormulario(request, response);
            request.getRequestDispatcher("Despesas/DespesasForm.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar despesa.");
            carregarCategoriasParaFormulario(request, response);
            request.getRequestDispatcher("Despesas/DespesasForm.jsp").forward(request, response);
        }
    }

    private void editarDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
            String descricao = request.getParameter("descricao");
            BigDecimal valor = new BigDecimal(request.getParameter("valor"));
            Date dataDespesa = Date.valueOf(request.getParameter("dataDespesa"));

            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
            if (categoria == null) {
                request.setAttribute("erro", "Categoria inválida.");
                carregarDadosParaEdicao(request, response, id);
                return;
            }

            Despesa despesa = new Despesa();
            despesa.setId(id);
            despesa.setUsuario(usuarioLogado);
            despesa.setCategoria(categoria);
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            despesa.setDataDespesa(dataDespesa);

            despesaDAO.atualizar(despesa);

            Log log = new Log();
            log.setUsuario(usuarioLogado);
            log.setAcao("Edição de Despesa");
            log.setDetalhes("Despesa ID " + id + " ('" + descricao + "') atualizada.");
            logDAO.registrar(log);

            response.sendRedirect(request.getContextPath() + "/despesas?action=listar&edicaoSucesso=true");

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "Dados inválidos para a despesa.");
            int id = Integer.parseInt(request.getParameter("id"));
            carregarDadosParaEdicao(request, response, id);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao editar despesa.");
            int id = Integer.parseInt(request.getParameter("id"));
            carregarDadosParaEdicao(request, response, id);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("listar".equals(action)) {
            listarDespesas(request, response);
        } else if ("form".equals(action)) {
            carregarCategoriasParaFormulario(request, response);
            request.getRequestDispatcher("Despesas/DespesasForm.jsp").forward(request, response);
        } else if ("editarForm".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            carregarDadosParaEdicao(request, response, id);
        } else if ("excluir".equals(action)) {
            excluirDespesa(request, response);
        }
    }

    private void listarDespesas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Despesa> despesas = despesaDAO.listarPorUsuario(usuarioLogado.getId());
            request.setAttribute("despesas", despesas);
            request.getRequestDispatcher("Despesas/DespesasList.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao listar despesas.");
            request.getRequestDispatcher("Despesas/DespesasList.jsp").forward(request, response);
        }
    }

    private void carregarCategoriasParaFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Categoria> categorias = categoriaDAO.listarTodos();
            request.setAttribute("categorias", categorias);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar categorias.");
        }
    }

    private void carregarDadosParaEdicao(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Despesa despesa = despesaDAO.buscarPorId(id);
            if (despesa != null && despesa.getUsuario().getId() == usuarioLogado.getId()) {
                List<Categoria> categorias = categoriaDAO.listarTodos();
                request.setAttribute("despesa", despesa);
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("Despesas/DespesasForm.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Despesa não encontrada ou não pertence ao usuário.");
                listarDespesas(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados para edição.");
            listarDespesas(request, response);
        }
    }

    private void excluirDespesa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Despesa despesaExcluida = despesaDAO.buscarPorId(id);
            if (despesaExcluida != null && despesaExcluida.getUsuario().getId() == usuarioLogado.getId()) {
                despesaDAO.excluir(id, usuarioLogado.getId());

                Log log = new Log();
                log.setUsuario(usuarioLogado);
                log.setAcao("Exclusão de Despesa");
                log.setDetalhes("Despesa ID " + id + " ('" + despesaExcluida.getDescricao() + "') excluída.");
                logDAO.registrar(log);

                response.sendRedirect(request.getContextPath() + "/despesas?action=listar&exclusaoSucesso=true");
                return;
            } else {
                request.setAttribute("erro", "Despesa não encontrada ou não pertence ao usuário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao excluir despesa.");
        }
        listarDespesas(request, response);
    }
}