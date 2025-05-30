package src.view;
import javax.swing.*;

import src.dao.ImpressoraDAO;
import src.model.Impressora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastrarImpressora extends JFrame {
    private JTextField txtNome;
    private JTextField txtConsumo;
    private JTextField txtValorKwh;
    private JTextField txtLucro;
    private JButton btnCadastrar;

    public TelaCadastrarImpressora() {
        setTitle("Cadastro de Impressora");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 20, 150, 25);
        add(txtNome);

        JLabel lblConsumo = new JLabel("Consumo (Watts):");
        lblConsumo.setBounds(20, 60, 120, 25);
        add(lblConsumo);

        txtConsumo = new JTextField();
        txtConsumo.setBounds(150, 60, 150, 25);
        add(txtConsumo);

        JLabel lblKwh = new JLabel("Valor do kWh (R$):");
        lblKwh.setBounds(20, 100, 120, 25);
        add(lblKwh);

        txtValorKwh = new JTextField();
        txtValorKwh.setBounds(150, 100, 150, 25);
        add(txtValorKwh);

        JLabel lblLucro = new JLabel("Lucro (%):");
        lblLucro.setBounds(20, 140, 120, 25);
        add(lblLucro);

        txtLucro = new JTextField();
        txtLucro.setBounds(150, 140, 150, 25);
        add(txtLucro);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(100, 190, 120, 30);
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText();
                    double consumo = Double.parseDouble(txtConsumo.getText());
                    double valorKwh = Double.parseDouble(txtValorKwh.getText());
                    double lucro = Double.parseDouble(txtLucro.getText());

                    Impressora impressora = new Impressora(0, nome, consumo, valorKwh, lucro);
                    new ImpressoraDAO().cadastrarImpressora(impressora);

                    JOptionPane.showMessageDialog(null, "✅ Impressora cadastrada com sucesso!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar impressora: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
