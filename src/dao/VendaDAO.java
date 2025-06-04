package src.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.model.Venda;
import src.model.VendaDetalhada;
import src.util.ConexaoBanco;

public class VendaDAO {

    public void novaVenda(Venda venda) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "INSERT INTO Vendas (clienteId, filamentoId, gramasUtilizadas, minutosImpressao, precoTotal, statusPagamento) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setInt(2, venda.getFilamento().getId());
            stmt.setDouble(3, venda.getGramasUtilizadas());
            stmt.setInt(4, venda.getMinutosImpressao());
            stmt.setDouble(5, venda.getPrecoTotal());
            stmt.setString(6, venda.getStatusPagamento());
            stmt.executeUpdate(); 
            
        } catch (Exception e) {
            System.out.println("Erro ao registrar venda: " + e.getMessage());
        }
    }

    public List<VendaDetalhada> relatorioVendas() throws SQLException {
    List<VendaDetalhada> relatorio = new ArrayList<>();
    try (Connection conn = ConexaoBanco.getConnection()) {
        String sql =
            "SELECT V.id, C.nome AS cliente, F.tipo AS filamento, V.gramasUtilizadas, V.minutosImpressao, " +
            "V.precoTotal, V.statusPagamento " +
            "FROM Vendas V " +
            "JOIN Clientes C ON V.clienteId = C.id " +
            "JOIN Filamentos F ON V.filamentoId = F.id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            VendaDetalhada v = new VendaDetalhada(
                rs.getInt("id"),
                rs.getString("cliente"),
                rs.getString("filamento"),
                rs.getDouble("gramasUtilizadas"),
                rs.getInt("minutosImpressao"),
                rs.getDouble("precoTotal"),
                rs.getString("statusPagamento")
            );
            relatorio.add(v);
        }
    }
    return relatorio;
}
      
}
