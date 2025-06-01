package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Configuracao;
import modelo.Usuario;

public class ConfiguracaoDAO {

    public void salvar(Configuracao configuracao) throws SQLException {
        String sql = "INSERT INTO configuracoes (usuario_id, chave, valor) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE valor = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (configuracao.getUsuario() != null) {
                pstmt.setInt(1, configuracao.getUsuario().getId());
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setString(2, configuracao.getChave());
            pstmt.setString(3, configuracao.getValor());
            pstmt.setString(4, configuracao.getValor());
            pstmt.executeUpdate();
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public Configuracao buscarPorChave(int usuarioId, String chave) throws SQLException {
        String sql = "SELECT id, valor FROM configuracoes WHERE usuario_id = ? AND chave = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Configuracao configuracao = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, chave);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                configuracao = new Configuracao();
                configuracao.setId(rs.getInt("id"));
                Usuario usuario = new Usuario();
                usuario.setId(usuarioId);
                configuracao.setUsuario(usuario);
                configuracao.setChave(chave);
                configuracao.setValor(rs.getString("valor"));
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
        return configuracao;
    }

    public Configuracao buscarGlobalPorChave(String chave) throws SQLException {
        String sql = "SELECT id, valor FROM configuracoes WHERE usuario_id IS NULL AND chave = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Configuracao configuracao = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chave);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                configuracao = new Configuracao();
                configuracao.setId(rs.getInt("id"));
                configuracao.setChave(chave);
                configuracao.setValor(rs.getString("valor"));
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
        return configuracao;
    }
}