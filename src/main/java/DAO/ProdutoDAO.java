package DAO;

import Conexão.ConnectionFactory;
import Model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final Connection connection;

public ProdutoDAO() {
    this.connection = new ConnectionFactory().getConnection();
    }

public void adiciona(Produto produto) {
    String sql = "INSERT INTO produtos(descricao, codigoBarras, unidadeVenda, ultimaCompra, precoVenda, quantEstoque) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCodigoBarras());
            ps.setString(3, produto.getUnidadeVenda());
            ps.setDate(4, new java.sql.Date(produto.getUltimaCompra().getTime()));
            ps.setFloat(5, produto.getPrecoVenda());
            ps.setFloat(6, produto.getQuantEstoque());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar produto: " + e.getMessage(), e);
        }
    }

public List<Produto> getLista() {
    List<Produto> produtos = new ArrayList<>();
    String sql = "SELECT * FROM produtos";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCodigoBarras(rs.getString("codigoBarras"));
                produto.setUnidadeVenda(rs.getString("unidadeVenda"));
                produto.setUltimaCompra(rs.getDate("ultimaCompra"));
                produto.setPrecoVenda(rs.getFloat("precoVenda"));
                produto.setQuantEstoque(rs.getFloat("quantEstoque"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }
    
    // Adicione este método dentro da classe ProdutoDAO

public Produto getProdutoPorCodigo(String codigoBarras) {
    String sql = "SELECT * FROM produtos WHERE codigoBarras = ?";
    Produto produto = null;
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, codigoBarras);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCodigoBarras(rs.getString("codigoBarras"));
                produto.setUnidadeVenda(rs.getString("unidadeVenda"));
                produto.setUltimaCompra(rs.getDate("ultimaCompra"));
                produto.setPrecoVenda(rs.getFloat("precoVenda"));
                produto.setQuantEstoque(rs.getFloat("quantEstoque"));
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar produto por código: " + e.getMessage(), e);
    }
    return produto;
}

public void altera(Produto produto) {
    String sql = "UPDATE produtos SET descricao=?, codigoBarras=?, unidadeVenda=?, ultimaCompra=?, precoVenda=?, quantEstoque=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCodigoBarras());
            ps.setString(3, produto.getUnidadeVenda());
            ps.setDate(4, new java.sql.Date(produto.getUltimaCompra().getTime()));
            ps.setFloat(5, produto.getPrecoVenda());
            ps.setFloat(6, produto.getQuantEstoque());
            ps.setInt(7, produto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto: " + e.getMessage(), e);
        }
    }

public void remove(Produto produto) {
    String sql = "DELETE FROM produtos WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, produto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover produto: " + e.getMessage(), e);
        }
    }

// Adicione este método dentro da classe ProdutoDAO

public void atualizaEstoque(Produto produto) {
    String sql = "UPDATE produtos SET quantEstoque = ? WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setFloat(1, produto.getQuantEstoque());
        ps.setInt(2, produto.getId());
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao atualizar estoque do produto: " + e.getMessage(), e);
    }
}
}