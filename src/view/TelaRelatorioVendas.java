package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.dao.VendaDAO;
import src.model.VendaDetalhada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaRelatorioVendas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnCarregar;

    public TelaRelatorioVendas() {
        setTitle("Relatório de Vendas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Cliente", "Filamento", "Gramas", "Minutos", "Custo Total", "Status"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnCarregar = new JButton("Carregar Relatório");
        btnCarregar.addActionListener(this::carregarRelatorio);

        add(scroll, BorderLayout.CENTER);
        add(btnCarregar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarRelatorio(ActionEvent e) {
    try {
        VendaDAO vendaDAO = new VendaDAO();
        List<VendaDetalhada> vendas = vendaDAO.relatorioVendas();

        modelo.setRowCount(0);

        for (VendaDetalhada v : vendas) {
            modelo.addRow(new Object[]{
                v.getId(),
                v.getCliente(),
                v.getFilamento(),
                String.format("%.1f", v.getgramasUtilizadas()) + "g",
                v.getMinutos() + " min",
                String.format("R$ %.2f", v.getPrecoTotal()),
                v.getStatusPagamento()
            });
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + ex.getMessage());
    }
}
}
