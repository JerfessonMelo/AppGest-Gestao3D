package src.view;
import javax.swing.*;

import src.dao.VendaDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaRelatorioVendas extends JFrame {

    private JTextArea areaTexto;
    private JButton btnCarregar;

    public TelaRelatorioVendas() {
        setTitle("Relatório de Vendas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaTexto);

        btnCarregar = new JButton("Carregar Relatório");
        btnCarregar.addActionListener(this::carregarRelatorio);

        add(scroll, BorderLayout.CENTER);
        add(btnCarregar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarRelatorio(ActionEvent e) {
        try {
            VendaDAO vendaDAO = new VendaDAO();
            List<String> linhas = vendaDAO.relatorioVendas();
            areaTexto.setText("");
            for (String linha : linhas) {
                areaTexto.append(linha + "\n\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaRelatorioVendas::new);
    }
}
