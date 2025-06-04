package src.view;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Gestão 3D");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastro = new JMenu("Cadastro");
        JMenu menuVendas = new JMenu("Vendas");
        JMenu menuListagem = new JMenu("Listagens");

        JMenuItem clienteItem = new JMenuItem("Cadastrar Cliente");
        JMenuItem filamentoItem = new JMenuItem("Cadastrar Filamento");
        JMenuItem impressoraItem = new JMenuItem("Cadastrar Impressora");

        JMenuItem registrarVendaItem = new JMenuItem("Registrar Venda");
        JMenuItem relatorioItem = new JMenuItem("Relatório de Vendas");

        JMenuItem listarClientesItem = new JMenuItem("Listar Clientes");
        JMenuItem listarFilamentosItem = new JMenuItem("Listar Filamentos");
        JMenuItem listarImpressorasItem = new JMenuItem("Listar Impressoras");

        clienteItem.addActionListener(e -> new TelaCadastroCliente().setVisible(true));
        filamentoItem.addActionListener(e -> new TelaCadastroFilamento().setVisible(true));
        impressoraItem.addActionListener(e -> new TelaCadastrarImpressora().setVisible(true));

        registrarVendaItem.addActionListener(e -> new TelaRegistrarVenda().setVisible(true));
        relatorioItem.addActionListener(e -> new TelaRelatorioVendas().setVisible(true));

        listarClientesItem.addActionListener(e -> new TelaListagemClientes().setVisible(true));
        listarFilamentosItem.addActionListener(e -> new TelaListagemFilamentos().setVisible(true));
        listarImpressorasItem.addActionListener(e -> new TelaListagemImpressoras().setVisible(true));

        menuCadastro.add(clienteItem);
        menuCadastro.add(filamentoItem);
        menuCadastro.add(impressoraItem);

        menuVendas.add(registrarVendaItem);
        menuVendas.add(relatorioItem);

        menuListagem.add(listarClientesItem);
        menuListagem.add(listarFilamentosItem);
        menuListagem.add(listarImpressorasItem);

        menuBar.add(menuCadastro);
        menuBar.add(menuVendas);
        menuBar.add(menuListagem);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
