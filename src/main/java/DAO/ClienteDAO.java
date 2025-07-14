package DAO;

import Model.Cliente;
import Conexão.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Connection connection;

    public ClienteDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome, cpf, endereco, cidade, cep, UF) VALUES(?,?,?,?,?,?)";
        // Usando try-with-resources para garantir que o PreparedStatement seja fechado
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());
            ps.setString(5, cliente.getCep());
            ps.setString(6, cliente.getUF());
            ps.executeUpdate();
            System.out.println("Cliente " + cliente.getNome() + " adicionado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar cliente: " + e.getMessage(), e);
        }
    }

    public List<Cliente> getLista() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id")); // Adicionando a leitura do ID
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setCep(rs.getString("cep"));
                cliente.setUF(rs.getString("UF"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    public void altera(Cliente cliente) {
        String sql = "UPDATE clientes SET nome=?, cpf=?, endereco=?, cidade=?, cep=?, UF=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());
            ps.setString(5, cliente.getCep());
            ps.setString(6, cliente.getUF());
            ps.setInt(7, cliente.getId());
            ps.executeUpdate();
             System.out.println("Cliente " + cliente.getNome() + " alterado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar cliente: " + e.getMessage(), e);
        }
    }

    public void remove(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cliente.getId());
            ps.executeUpdate();
             System.out.println("Cliente " + cliente.getNome() + " removido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover cliente: " + e.getMessage(), e);
        }
    }

    // O método getClienteId não está sendo usado na tela, mas é bom tê-lo corrigido também.
    public Cliente getClienteId(int idCli) {
        String sql = "SELECT * FROM clientes WHERE id=?"; // Corrigido de 'cliente' para 'clientes'
        Cliente cliente = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCli);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setCidade(rs.getString("cidade"));
                    cliente.setCep(rs.getString("cep"));
                    cliente.setUF(rs.getString("UF"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por ID: " + e.getMessage(), e);
        }
        return cliente;
    }
}