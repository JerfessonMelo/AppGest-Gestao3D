package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.dao.ClienteDAO;
import src.model.Cliente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaListagemClientes extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnCarregar;

    public TelaListagemClientes() {
        setTitle("Listagem de Clientes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnCarregar = new JButton("Carregar Clientes");
        btnCarregar.addActionListener(this::carregarClientes);

        add(scroll, BorderLayout.CENTER);
        add(btnCarregar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarClientes(ActionEvent e) {
        try {
            ClienteDAO dao = new ClienteDAO();
            List<Cliente> clientes = dao.listaClientes();
            modelo.setRowCount(0);
            for (Cliente c : clientes) {
                modelo.addRow(new Object[]{c.getId(), c.getNome()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
}
