package src.view;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Gestão 3D");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastro = new JMenu("Cadastro");
        JMenuItem clienteItem = new JMenuItem("Cadastrar Cliente");
        JMenuItem filamentoItem = new JMenuItem("Cadastrar Filamento");
        JMenuItem impressoraItem = new JMenuItem("Cadastrar Impressora");

        JMenu menuVendas = new JMenu("Vendas");
        JMenuItem registrarVendaItem = new JMenuItem("Registrar Venda");
        JMenuItem relatorioItem = new JMenuItem("Relatório de Vendas");

        clienteItem.addActionListener(e -> new TelaCadastroCliente().setVisible(true));
        filamentoItem.addActionListener(e -> new TelaCadastroFilamento().setVisible(true));
        impressoraItem.addActionListener(e -> new TelaCadastrarImpressora().setVisible(true));
        registrarVendaItem.addActionListener(e -> new TelaRegistrarVenda().setVisible(true));
        relatorioItem.addActionListener(e -> new TelaRelatorioVendas().setVisible(true));

        menuCadastro.add(clienteItem);
        menuCadastro.add(filamentoItem);
        menuCadastro.add(impressoraItem);

        menuVendas.add(registrarVendaItem);
        menuVendas.add(relatorioItem);

        menuBar.add(menuCadastro);
        menuBar.add(menuVendas);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}