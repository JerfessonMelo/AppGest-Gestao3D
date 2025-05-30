package src.dao;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.model.Cliente;
import src.util.ConexaoBanco;

public class ClienteDAO {
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "INSERT INTO Clientes (nome) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    public List<Cliente> listaClientes() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexaoBanco.getConnection()) {
            String sql = "SELECT id, nome FROM Clientes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("id"), rs.getString("nome")));
            }
        } catch (Exception e) {
            System.out.println("Erro ao lista clientes " + e.getMessage());
        }
        return lista;
    }
}
