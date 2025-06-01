package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

public class CategoriaDAO {

    public void cadastrar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nome, descricao) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                categoria.setId(generatedKeys.getInt(1));
            }
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public Categoria buscarPorId(int id) throws SQLException {
        String sql = "SELECT nome, descricao FROM categorias WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Categoria categoria = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setId(id);
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));
            }
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return categoria;
    }

    public List<Categoria> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, descricao FROM categorias";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Categoria> categorias = new ArrayList<>();

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));
                categorias.add(categoria);
            }
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return categorias;
    }

    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nome = ?, descricao = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());
            pstmt.setInt(3, categoria.getId());
            pstmt.executeUpdate();
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}