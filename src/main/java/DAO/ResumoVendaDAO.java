package DAO;

import Conexão.ConnectionFactory;
import Model.ResumoVenda;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResumoVendaDAO {
    private final Connection connection;

    public ResumoVendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public List<ResumoVenda> getResumoPorData(LocalDate data) {
        List<ResumoVenda> resumos = new ArrayList<>();
        String sql = "SELECT formaPag, SUM(valorVenda) as total_valor " +
                     "FROM vendas " +
                     "WHERE dataVenda >= ? AND dataVenda < ? " +
                     "GROUP BY formaPag";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            Timestamp inicioDoDia = Timestamp.valueOf(data.atStartOfDay());
            Timestamp fimDoDia = Timestamp.valueOf(data.plusDays(1).atStartOfDay());

            // --- MENSAGEM DE DEPURAÇÃO ---
            System.out.println("DAO: Buscando vendas entre " + inicioDoDia + " e " + fimDoDia);

            ps.setTimestamp(1, inicioDoDia);
            ps.setTimestamp(2, fimDoDia);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ResumoVenda resumo = new ResumoVenda();
                    resumo.setDataVenda(Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    resumo.setFormaPag(rs.getInt("formaPag"));
                    resumo.setValorFormaPag(rs.getFloat("total_valor"));
                    resumos.add(resumo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar resumo de vendas: " + e.getMessage(), e);
        }
        
        // --- MENSAGEM DE DEPURAÇÃO ---
        System.out.println("DAO: Encontrados " + resumos.size() + " registros de resumo.");
        
        return resumos;
    }
}