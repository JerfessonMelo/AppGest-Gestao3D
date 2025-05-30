package src.view;
import javax.swing.*;

import src.dao.FilamentoDAO;
import src.model.Filamento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroFilamento extends JFrame {
    private JTextField campoTipo;
    private JTextField campoPrecoKg;
    private JTextField campoDensidade;
    private JButton btnSalvar;
    private JButton btnVoltar;

    public TelaCadastroFilamento() {
        setTitle("Cadastro de Filamento");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(30, 30, 100, 25);
        add(lblTipo);

        campoTipo = new JTextField();
        campoTipo.setBounds(130, 30, 200, 25);
        add(campoTipo);

        JLabel lblPrecoKg = new JLabel("Preço por Kg (R$):");
        lblPrecoKg.setBounds(30, 70, 120, 25);
        add(lblPrecoKg);

        campoPrecoKg = new JTextField();
        campoPrecoKg.setBounds(160, 70, 170, 25);
        add(campoPrecoKg);

        JLabel lblDensidade = new JLabel("Densidade:");
        lblDensidade.setBounds(30, 110, 100, 25);
        add(lblDensidade);

        campoDensidade = new JTextField();
        campoDensidade.setBounds(130, 110, 200, 25);
        add(campoDensidade);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 160, 120, 30);
        add(btnSalvar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(200, 160, 120, 30);
        add(btnVoltar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = campoTipo.getText();
                String precoText = campoPrecoKg.getText();
                String densidadeText = campoDensidade.getText();

                if (tipo.isEmpty() || precoText.isEmpty() || densidadeText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                    return;
                }

                try {
                    int precoKg = Integer.parseInt(precoText);
                    double densidade = Double.parseDouble(densidadeText);

                    FilamentoDAO filamentoDAO = new FilamentoDAO();
                    filamentoDAO.cadastrarFilamento(new Filamento(0, tipo, precoKg, densidade));
                    JOptionPane.showMessageDialog(null, "✅ Filamento cadastrado com sucesso!");
                    campoTipo.setText("");
                    campoPrecoKg.setText("");
                    campoDensidade.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar filamento: " + ex.getMessage());
                }
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }
}
