package DAO;

import Conexão.ConnectionFactory;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nomeDeLogin, senha, tipoUsuario) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNomeDeLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getTipoDeUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário: " + e.getMessage(), e);
        }
    }

    public List<Usuario> getLista() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeDeLogin(rs.getString("nomeDeLogin"));
                // Propositalmente não carregamos a senha aqui por segurança
                usuario.setTipoDeUsuario(rs.getString("tipoUsuario"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return usuarios;
    }

    public void altera(Usuario usuario) {
        String sql = "UPDATE usuarios SET nomeDeLogin=?, senha=?, tipoUsuario=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNomeDeLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getTipoDeUsuario());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar usuário: " + e.getMessage(), e);
        }
    }

    public void remove(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário: " + e.getMessage(), e);
        }
    }

    // O método de autenticação que já criamos
    public Usuario autenticar(String nomeDeLogin, String senha) {
        String sql = "SELECT * FROM usuarios WHERE nomeDeLogin = ? AND senha = ?";
        Usuario usuario = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeDeLogin);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNomeDeLogin(rs.getString("nomeDeLogin"));
                    usuario.setTipoDeUsuario(rs.getString("tipoUsuario"));
                    usuario.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
        return usuario;
    }
}