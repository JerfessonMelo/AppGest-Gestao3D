package src.model;
import java.sql.SQLException;
import src.dao.VendaDAO;

public class Relatorio {

    public void relatorioVendas() throws SQLException {
        VendaDAO vendaDAO = new VendaDAO();
        System.out.println("\n Relatorio de Vendas");
        for (String linha: vendaDAO.relatorioVendas()) {
            System.out.println(linha);
        }
    }
    
}
