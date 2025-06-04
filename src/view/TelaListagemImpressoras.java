package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.dao.ImpressoraDAO;
import src.model.Impressora;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaListagemImpressoras extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnCarregar;

    public TelaListagemImpressoras() {
        setTitle("Listagem de Impressoras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Consumo (Watts)", "Valor kWh (R$)"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnCarregar = new JButton("Carregar Impressoras");
        btnCarregar.addActionListener(this::carregarImpressoras);

        add(scroll, BorderLayout.CENTER);
        add(btnCarregar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarImpressoras(ActionEvent e) {
        try {
            ImpressoraDAO dao = new ImpressoraDAO();
            List<Impressora> lista = dao.listaImpressoras();
            modelo.setRowCount(0);
            for (Impressora i : lista) {
                modelo.addRow(new Object[]{
                    i.getId(),
                    i.getNome(),
                    i.getConsumoWatts(),
                    String.format("R$ %.2f", i.getValorKwH())
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar impressoras: " + ex.getMessage());
        }
    }
}
