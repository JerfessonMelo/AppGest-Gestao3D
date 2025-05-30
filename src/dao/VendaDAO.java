package src.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.model.Venda;
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

    public List<String> relatorioVendas() throws SQLException {
        List<String> Relatorio = new ArrayList<>();
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = 
            "SELECT V.id, C.nome, F.tipo, V.precoTotal, V.statusPagamento " +
            "FROM Vendas V " +                                                 
            "JOIN Clientes C ON V.clienteId = C.id " +                        
            "JOIN Filamentos F ON V.filamentoId = F.id"; 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String linha = String.format("Venda #%d | Cliente: %s | Filamento: %s | Total R$: %.2f |  %s", 
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getDouble("precoTotal"),
                rs.getString("statusPagamento")
                );
                Relatorio.add(linha);
            }
        }
        return Relatorio;
    }   
}
