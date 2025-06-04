package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.dao.FilamentoDAO;
import src.model.Filamento;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaListagemFilamentos extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnCarregar;

    public TelaListagemFilamentos() {
        setTitle("Listagem de Filamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Tipo", "Preço (R$/kg)", "Densidade (g/cm³)"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnCarregar = new JButton("Carregar Filamentos");
        btnCarregar.addActionListener(this::carregarFilamentos);

        add(scroll, BorderLayout.CENTER);
        add(btnCarregar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarFilamentos(ActionEvent e) {
        try {
            FilamentoDAO dao = new FilamentoDAO();
            List<Filamento> lista = dao.listaFilamentos();
            modelo.setRowCount(0);
            for (Filamento f : lista) {
                modelo.addRow(new Object[]{
                    f.getId(),
                    f.getTipo(),
                    "R$ " + f.getPrecoKg(),
                    f.getDensidade()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filamentos: " + ex.getMessage());
        }
    }
}
