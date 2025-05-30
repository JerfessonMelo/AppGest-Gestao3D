package src.dao;
import java.util.Scanner;

import src.model.Cliente;
import src.model.Filamento;
import src.model.Impressora;
import src.model.Relatorio;
import src.model.Venda;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final FilamentoDAO filamentoDAO = new FilamentoDAO();
    private static final VendaDAO vendaDAO = new VendaDAO();
    private static final ImpressoraDAO impressoraDAO = new ImpressoraDAO();
    private static final Relatorio relatorio = new Relatorio();

    public static void main(String[] args) throws Exception {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Cadastrar filamento");
            System.out.println("3. Registrar venda");
            System.out.println("4. Ver relatório de vendas");
            System.out.println("5. Ver Clientes Cadastrados");
            System.out.println("6. Ver Filamentos Cadastrados");
            System.out.println("7. Cadastrar Impressora");
            System.out.println("8. Ver Impressoras");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt(); scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarFilamento();
                case 3 -> registrarVenda();
                case 4 -> relatorio.relatorioVendas();
                case 5 -> listaClientes();
                case 6 -> listaFilamentos();
                case 7 -> cadastrarImpressora();
                case 8 -> listarImpressoras();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarCliente() throws Exception {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        clienteDAO.cadastrarCliente(new Cliente(nome));
        System.out.println("✅ Cliente cadastrado!");
    }

    private static void cadastrarFilamento() throws Exception {
        System.out.print("Tipo de filamento: ");
        String tipo = scanner.nextLine();
        System.out.print("Preço por Kg (R$): ");
        int preco = scanner.nextInt();
        System.out.print("Densidade do filamento: ");
        double densidade = scanner.nextDouble(); scanner.nextLine();
        filamentoDAO.cadastrarFilamento(new Filamento(0, tipo, preco, densidade));
        System.out.println("✅ Filamento cadastrado!");
    }

    private static void cadastrarImpressora() throws Exception {
        System.out.print("Nome da impressora: ");
        String nome = scanner.nextLine();
        System.out.print("Consumo (watts): ");
        double consumoWatts = scanner.nextDouble();
        System.out.print("Valor do kWh (R$): ");
        double valorKwH = scanner.nextDouble();
        System.out.print("Percentual de lucro desejado (%): ");
        double lucro = scanner.nextDouble(); scanner.nextLine();

        impressoraDAO.cadastrarImpressora(new Impressora(0, nome, consumoWatts, valorKwH, lucro));
        System.out.println("✅ Impressora cadastrada!");
    }

    private static void registrarVenda() throws Exception {
        System.out.println("\n--- Clientes ---");
        for (Cliente c : clienteDAO.listaClientes())
            System.out.printf("ID: %d | Nome: %s\n", c.getId(), c.getNome());
        System.out.print("ID do cliente: ");
        int clienteId = scanner.nextInt(); scanner.nextLine();

        System.out.println("\n--- Filamentos ---");
        for (Filamento f : filamentoDAO.listaFilamentos())
            System.out.printf("ID: %d | Tipo: %s | Preço: R$ %d\n", f.getId(), f.getTipo(), f.getPrecoKg());
        System.out.print("ID do filamento: ");
        int filamentoId = scanner.nextInt();

        System.out.println("\n--- Impressoras ---");
        for (Impressora imp : impressoraDAO.listaImpressoras())
            System.out.printf("ID: %d | Nome: %s | %,.0fW | R$ %.2f/kWh | Lucro: %.0f%%\n",
                    imp.getId(), imp.getNome(), imp.getConsumoWatts(), imp.getValorKwH(), imp.getPercentualLucro());
        System.out.print("ID da impressora: ");
        int impressoraId = scanner.nextInt();

        System.out.print("Gramas utilizadas: ");
        double gramas = scanner.nextDouble();
        System.out.print("Minutos de impressão: ");
        int minutos = scanner.nextInt(); scanner.nextLine();

        System.out.print("Status de pagamento (Pago/Pendente): ");
        String status = scanner.nextLine();

        int precoKg = filamentoDAO.obterPrecoFilamento(filamentoId);
        Impressora impressora = impressoraDAO.buscarImpressoraPorId(impressoraId);

        double custoMaterial = (gramas / 1000.0) * precoKg;
        double consumoKWh = (minutos / 60.0) * impressora.getConsumoWatts() / 1000.0;
        double custoEnergia = consumoKWh * impressora.getValorKwH();
        double custoTotal = custoMaterial + custoEnergia;
        double precoFinal = custoTotal * (1 + (impressora.getPercentualLucro() / 100.0));

        Cliente cliente = new Cliente(clienteId, "");
        Filamento filamento = new Filamento(filamentoId, "", precoKg, 0);
        Venda venda = new Venda(cliente, filamento, status, gramas, minutos, precoFinal);

        vendaDAO.novaVenda(venda);
        System.out.printf("✅ Venda registrada com sucesso. Total a cobrar: R$ %.2f\n", precoFinal);
    }

    private static void listaClientes() throws Exception {
        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente c : clienteDAO.listaClientes())
            System.out.printf("ID: %d | Nome: %s\n", c.getId(), c.getNome());
    }

    private static void listaFilamentos() throws Exception {
        System.out.println("\n--- Lista de Filamentos ---");
        for (Filamento f : filamentoDAO.listaFilamentos())
            System.out.printf("ID: %d | Tipo: %s | Preço: R$ %d | Densidade: %.2f\n",
                    f.getId(), f.getTipo(), f.getPrecoKg(), f.getDensidade());
    }

    private static void listarImpressoras() throws Exception {
        System.out.println("\n--- Lista de Impressoras ---");
        for (Impressora imp : impressoraDAO.listaImpressoras())
            System.out.printf("ID: %d | Nome: %s | %,.0fW | R$ %.2f/kWh | Lucro: %.0f%%\n",
                    imp.getId(), imp.getNome(), imp.getConsumoWatts(), imp.getValorKwH(), imp.getPercentualLucro());
    }
}
