package src.dao;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.model.Impressora;
import src.util.ConexaoBanco;

public class ImpressoraDAO {
    public void cadastrarImpressora(Impressora impressora) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "INSERT INTO Impressoras (nome, consumoWatts, valorKwH) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, impressora.getNome());
            stmt.setDouble(2, impressora.getConsumoWatts());
            stmt.setDouble(3, impressora.getValorKwH());
            stmt.executeUpdate();
        }
    }

    public List<Impressora> listaImpressoras() throws SQLException {
        List<Impressora> lista = new ArrayList<>();
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT * FROM Impressoras";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Impressora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("consumoWatts"),
                    rs.getDouble("valorKwH")
                ));
            }
        }
        return lista;
    }

    public Impressora buscarImpressoraPorId(int id) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT * FROM Impressoras WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Impressora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("consumoWatts"),
                    rs.getDouble("valorKwH")
                );
            } else {
                throw new SQLException("Impressora não encontrada.");
            }
        }
    }
}
