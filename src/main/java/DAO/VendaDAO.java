package DAO;

import Conexão.ConnectionFactory;
import Model.Venda;
import Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private final Connection connection;

    public VendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public int adiciona(Venda venda) {
        String sql = "INSERT INTO vendas(dataVenda, idCli, formaPag, valorVenda) VALUES(?,?,?,?)";
        // Usamos Statement.RETURN_GENERATED_KEYS para obter o ID da venda criada
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            
            if (venda.getIdCli() != null) {
                ps.setInt(2, venda.getIdCli().getId());
            } else {
                ps.setNull(2, Types.INTEGER); // Permite cliente nulo (Consumidor Padrão)
            }
            
            ps.setInt(3, venda.getFormaPag());
            ps.setFloat(4, venda.getValorVenda());
            ps.executeUpdate();

            // Recupera o ID gerado pelo banco de dados
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID da venda
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar venda: " + e.getMessage(), e);
        }
        return 0; // Retorna 0 se falhar
    }
    
    // Outros métodos como getLista, altera, remove podem ser corrigidos da mesma forma se necessário.
}