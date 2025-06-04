package src.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.model.Filamento;
import src.util.ConexaoBanco;

public class FilamentoDAO {

    public void cadastrarFilamento(Filamento filamento) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "INSERT INTO Filamentos (tipo, precoKg, densidade) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, filamento.getTipo());
            stmt.setInt(2, filamento.getPrecoKg());
            stmt.setDouble(3, filamento.getDensidade());
            stmt.executeUpdate();   
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar filamento: " + e.getMessage());
        }
    }

    public List<Filamento> listaFilamentos() throws SQLException {
        List<Filamento> lista = new ArrayList<>();
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT id, tipo, precoKg, densidade FROM Filamentos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Filamento(rs.getInt("id"), 
                rs.getString("tipo"), 
                rs.getInt("precoKg"), 
                rs.getDouble("densidade")));
            }
        } catch (Exception e) {
            System.out.println("Erro ao lista filamentos " + e.getMessage());
        }
        return lista;
    }

    public int obterPrecoFilamento(int id) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT precoKg, densidade FROM Filamentos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("precoKg");
            } else {
                throw new SQLException("Filamento não encontrado");
            }
        } 
    }

    public Filamento buscarPorId(int id) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT id, tipo, precoKg, densidade FROM Filamentos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Filamento(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getInt("precoKg"),
                    rs.getDouble("densidade")
                );
            } else {
                throw new SQLException("Filamento não encontrado");
            }
        }
    }
}
