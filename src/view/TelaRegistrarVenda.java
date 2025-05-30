package src.view;
import javax.swing.*;

import src.dao.ClienteDAO;
import src.dao.FilamentoDAO;
import src.dao.ImpressoraDAO;
import src.dao.VendaDAO;
import src.model.Cliente;
import src.model.Filamento;
import src.model.Impressora;
import src.model.Venda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaRegistrarVenda extends JFrame {
    private JComboBox<String> comboClientes;
    private JComboBox<String> comboFilamentos;
    private JComboBox<String> comboImpressoras;
    private JTextField txtGramas;
    private JTextField txtMinutos;
    private JTextField txtStatus;
    private JButton btnRegistrar;

    public TelaRegistrarVenda() {
        setTitle("Registrar Venda");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(20, 20, 100, 20);
        add(lblCliente);

        comboClientes = new JComboBox<>();
        comboClientes.setBounds(130, 20, 200, 20);
        add(comboClientes);

        JLabel lblFilamento = new JLabel("Filamento:");
        lblFilamento.setBounds(20, 50, 100, 20);
        add(lblFilamento);

        comboFilamentos = new JComboBox<>();
        comboFilamentos.setBounds(130, 50, 200, 20);
        add(comboFilamentos);

        JLabel lblImpressora = new JLabel("Impressora:");
        lblImpressora.setBounds(20, 80, 100, 20);
        add(lblImpressora);

        comboImpressoras = new JComboBox<>();
        comboImpressoras.setBounds(130, 80, 200, 20);
        add(comboImpressoras);

        JLabel lblGramas = new JLabel("Gramas:");
        lblGramas.setBounds(20, 110, 100, 20);
        add(lblGramas);

        txtGramas = new JTextField();
        txtGramas.setBounds(130, 110, 100, 20);
        add(txtGramas);

        JLabel lblMinutos = new JLabel("Minutos:");
        lblMinutos.setBounds(20, 140, 100, 20);
        add(lblMinutos);

        txtMinutos = new JTextField();
        txtMinutos.setBounds(130, 140, 100, 20);
        add(txtMinutos);

        JLabel lblStatus = new JLabel("Status Pagamento:");
        lblStatus.setBounds(20, 170, 150, 20);
        add(lblStatus);

        txtStatus = new JTextField("Pago ou Pendente");
        txtStatus.setBounds(130, 170, 150, 20);
        add(txtStatus);

        btnRegistrar = new JButton("Registrar Venda");
        btnRegistrar.setBounds(100, 210, 180, 30);
        add(btnRegistrar);

        preencherCombos();
        registrarVenda();

        setVisible(true);
    }

    private void preencherCombos() {
        try {
            for (Cliente c : new ClienteDAO().listaClientes()) {
                comboClientes.addItem(c.getId() + " - " + c.getNome());
            }

            for (Filamento f : new FilamentoDAO().listaFilamentos()) {
                comboFilamentos.addItem(f.getId() + " - " + f.getTipo());
            }

            for (Impressora i : new ImpressoraDAO().listaImpressoras()) {
                comboImpressoras.addItem(i.getId() + " - " + i.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void registrarVenda() {
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int clienteId = Integer.parseInt(comboClientes.getSelectedItem().toString().split(" - ")[0]);
                    int filamentoId = Integer.parseInt(comboFilamentos.getSelectedItem().toString().split(" - ")[0]);
                    int impressoraId = Integer.parseInt(comboImpressoras.getSelectedItem().toString().split(" - ")[0]);

                    double gramas = Double.parseDouble(txtGramas.getText());
                    int minutos = Integer.parseInt(txtMinutos.getText());
                    String status = txtStatus.getText();

                    int precoKg = new FilamentoDAO().obterPrecoFilamento(filamentoId);
                    Impressora impressora = new ImpressoraDAO().buscarImpressoraPorId(impressoraId);

                    double custoMaterial = (gramas / 1000.0) * precoKg;
                    double consumoKWh = (minutos / 60.0) * impressora.getConsumoWatts() / 1000.0;
                    double custoEnergia = consumoKWh * impressora.getValorKwH();
                    double custoTotal = custoMaterial + custoEnergia;
                    double precoFinal = custoTotal * (1 + impressora.getPercentualLucro() / 100.0);

                    Venda venda = new Venda(
                            new Cliente(clienteId, ""),
                            new Filamento(filamentoId, "", precoKg, 0),
                            status,
                            gramas,
                            minutos,
                            precoFinal
                    );

                    new VendaDAO().novaVenda(venda);

                    JOptionPane.showMessageDialog(null, String.format("Venda registrada!\nTotal a cobrar: R$ %.2f", precoFinal));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + ex.getMessage());
                }
            }
        });
    }
}
