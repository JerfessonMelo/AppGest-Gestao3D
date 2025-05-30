package src.view;
import javax.swing.*;

import src.dao.ClienteDAO;
import src.model.Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroCliente extends JFrame {
    private JTextField campoNome;
    private JButton btnSalvar;
    private JButton btnVoltar;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 100, 25);
        add(lblNome);

        campoNome = new JTextField();
        campoNome.setBounds(130, 30, 200, 25);
        add(campoNome);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 100, 120, 30);
        add(btnSalvar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(200, 100, 120, 30);
        add(btnVoltar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
                    return;
                }

                try {
                    ClienteDAO clienteDAO = new ClienteDAO();
                    clienteDAO.cadastrarCliente(new Cliente(nome));
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                    campoNome.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + ex.getMessage());
                }
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }
}
