package DAO;

import Conexão.ConnectionFactory;
import Model.ItemVenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemVendaDAO {
    private final Connection connection;

    public ItemVendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(ItemVenda item) {
        String sql = "insert into itemvenda(idVenda, produto, quantVendida, precoVenda) "
                + "VALUES(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, item.getIdVenda().getId());
            ps.setInt(2, item.getProduto().getId());
            ps.setInt(3, item.getQuantVendida());
            ps.setFloat(4, item.getPrecoVenda());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar item de venda: " + e.getMessage(), e);
        }
    }
}