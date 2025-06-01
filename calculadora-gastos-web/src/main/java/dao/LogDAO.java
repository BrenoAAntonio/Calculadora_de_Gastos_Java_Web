package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import modelo.Log;
import modelo.Usuario;

public class LogDAO {

    public void registrar(Log log) throws SQLException {
        String sql = "INSERT INTO logs (usuario_id, acao, detalhes) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (log.getUsuario() != null) {
                pstmt.setInt(1, log.getUsuario().getId());
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setString(2, log.getAcao());
            pstmt.setString(3, log.getDetalhes());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                log.setId(generatedKeys.getInt(1));
            }
        } finally {
            Conexao.closeConnection(conn);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public Log buscarPorId(int id) throws SQLException {
        String sql = "SELECT l.acao, l.detalhes, l.data_log, " +
                     "u.id AS usuario_id, u.login, u.nome AS usuario_nome " +
                     "FROM logs l " +
                     "LEFT JOIN usuarios u ON l.usuario_id = u.id " +
                     "WHERE l.id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Log log = null;

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                log = new Log();
                log.setId(id);
                log.setAcao(rs.getString("acao"));
                log.setDetalhes(rs.getString("detalhes"));
                log.setDataLog(rs.getTimestamp("data_log"));

                int usuarioId = rs.getInt("usuario_id");
                if (usuarioId > 0) {
                    Usuario usuario = new Usuario();
                    usuario.setId(usuarioId);
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("usuario_nome"));
                    log.setUsuario(usuario);
                }
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
        return log;
    }

    public List<Log> listarTodos() throws SQLException {
        String sql = "SELECT l.id, l.acao, l.detalhes, l.data_log, " +
                     "u.id AS usuario_id, u.login, u.nome AS usuario_nome " +
                     "FROM logs l " +
                     "LEFT JOIN usuarios u ON l.usuario_id = u.id " +
                     "ORDER BY l.data_log DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Log> logs = new ArrayList<>();

        try {
            conn = Conexao.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Log log = new Log();
                log.setId(rs.getInt("id"));
                log.setAcao(rs.getString("acao"));
                log.setDetalhes(rs.getString("detalhes"));
                log.setDataLog(rs.getTimestamp("data_log"));

                int usuarioId = rs.getInt("usuario_id");
                if (usuarioId > 0) {
                    Usuario usuario = new Usuario();
                    usuario.setId(usuarioId);
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("usuario_nome"));
                    log.setUsuario(usuario);
                }
                logs.add(log);
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
        return logs;
    }
}