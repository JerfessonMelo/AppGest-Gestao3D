package src.view;

import javax.swing.*;

import src.dao.FilamentoDAO;
import src.model.Filamento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroFilamento extends JFrame {
    private JComboBox<String> comboTipo;
    private JTextField campoApelido;
    private JTextField campoPrecoKg;
    private JButton btnSalvar;
    private JButton btnVoltar;

    public TelaCadastroFilamento() {
        setTitle("Cadastro de Filamento");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTipo = new JLabel("Tipo de Filamento:");
        lblTipo.setBounds(30, 30, 150, 25);
        add(lblTipo);

        String[] tipos = {"PLA", "ABS", "PETG"};
        comboTipo = new JComboBox<>(tipos);
        comboTipo.setBounds(180, 30, 200, 25);
        add(comboTipo);

        JLabel lblApelido = new JLabel("Apelido:");
        lblApelido.setBounds(30, 70, 150, 25);
        add(lblApelido);

        campoApelido = new JTextField();
        campoApelido.setBounds(180, 70, 200, 25);
        add(campoApelido);

        JLabel lblPrecoKg = new JLabel("Preço por Kg (R$):");
        lblPrecoKg.setBounds(30, 110, 150, 25);
        add(lblPrecoKg);

        campoPrecoKg = new JTextField();
        campoPrecoKg.setBounds(180, 110, 200, 25);
        add(campoPrecoKg);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(80, 180, 120, 30);
        add(btnSalvar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(220, 180, 120, 30);
        add(btnVoltar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipo.getSelectedItem();
                String apelido = campoApelido.getText().trim();
                String precoText = campoPrecoKg.getText();

                if (apelido.isEmpty() || precoText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                    return;
                }

                try {
                    int precoKg = Integer.parseInt(precoText);
                    double densidade;

                    switch (tipo) {
                        case "PLA": densidade = 1.24; break;
                        case "ABS": densidade = 1.04; break;
                        case "PETG": densidade = 1.27; break;
                        default: densidade = 1.20; break;
                    }

                    String nomeConcatenado = tipo + " " + apelido;

                    Filamento filamento = new Filamento(0, nomeConcatenado, precoKg, densidade);
                    new FilamentoDAO().cadastrarFilamento(filamento);

                    String msg = String.format("Filamento: %s\nPreço: R$ %d\nDensidade: %.2f g/cm³",
                            nomeConcatenado, precoKg, densidade);

                    JOptionPane.showMessageDialog(null, msg, "✅ Filamento cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor inválido para preço.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar filamento: " + ex.getMessage());
                }
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }
}
