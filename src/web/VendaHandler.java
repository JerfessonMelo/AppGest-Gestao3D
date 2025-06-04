package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import src.dao.ClienteDAO;
import src.dao.FilamentoDAO;
import src.dao.ImpressoraDAO;
import src.dao.VendaDAO;
import src.model.Cliente;
import src.model.Filamento;
import src.model.Impressora;
import src.model.Venda;

public class VendaHandler implements HttpHandler {
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final FilamentoDAO filamentoDAO = new FilamentoDAO();
    private final ImpressoraDAO impressoraDAO = new ImpressoraDAO();
    private final VendaDAO vendaDAO = new VendaDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            if ("GET".equalsIgnoreCase(method)) {
                handleGet(exchange);
            } else if ("POST".equalsIgnoreCase(method)) {
                handlePost(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            String msg = "Erro interno";
            exchange.sendResponseHeaders(500, msg.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(msg.getBytes(StandardCharsets.UTF_8)); }
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            List<Cliente> clientes = clienteDAO.listaClientes();
            List<Filamento> filamentos = filamentoDAO.listaFilamentos();
            List<Impressora> impressoras = impressoraDAO.listaImpressoras();

            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h2>Registrar Venda</h2>");
            html.append("<form method='POST'>");
            html.append("Cliente: <select name='cliente'>");
            for (Cliente c : clientes) {
                html.append("<option value='").append(c.getId()).append("'>").append(c.getNome()).append("</option>");
            }
            html.append("</select><br>");
            html.append("Filamento: <select name='filamento'>");
            for (Filamento f : filamentos) {
                html.append("<option value='").append(f.getId()).append("'>").append(f.getTipo()).append("</option>");
            }
            html.append("</select><br>");
            html.append("Impressora: <select name='impressora'>");
            for (Impressora i : impressoras) {
                html.append("<option value='").append(i.getId()).append("'>").append(i.getNome()).append("</option>");
            }
            html.append("</select><br>");
            html.append("Metros utilizados: <input name='metros' type='number' step='0.01'><br>");
            html.append("Minutos: <input name='minutos' type='number'><br>");
            html.append("Lucro (%): <input name='lucro' type='number' step='0.01'><br>");
            html.append("Status: <select name='status'><option>Pago</option><option>Pendente</option></select><br>");
            html.append("<input type='submit' value='Registrar'>");
            html.append("</form><p><a href='/'>Voltar</a></p>");
            html.append("</body></html>");

            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
        } catch (Exception e) { throw new IOException(e); }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            body = URLDecoder.decode(body, StandardCharsets.UTF_8);
            int clienteId = 0, filamentoId = 0, impressoraId = 0, minutos = 0; double metros=0, lucro=0; String status="";
            for (String p : body.split("&")) {
                String[] kv = p.split("=");
                if (kv.length==2) {
                    switch(kv[0]) {
                        case "cliente" -> clienteId = Integer.parseInt(kv[1]);
                        case "filamento" -> filamentoId = Integer.parseInt(kv[1]);
                        case "impressora" -> impressoraId = Integer.parseInt(kv[1]);
                        case "metros" -> metros = Double.parseDouble(kv[1]);
                        case "minutos" -> minutos = Integer.parseInt(kv[1]);
                        case "lucro" -> lucro = Double.parseDouble(kv[1]);
                        case "status" -> status = kv[1];
                    }
                }
            }
            Filamento filamento = filamentoDAO.buscarPorId(filamentoId);
            Impressora impressora = impressoraDAO.buscarImpressoraPorId(impressoraId);
            double densidade = filamento.getDensidade();
            double raio = 1.75 / 2 / 10.0;
            double areaDiametro = Math.PI * Math.pow(raio,2);
            double comprimentoCm = metros * 100.0;
            double gramas = areaDiametro * comprimentoCm * densidade;
            double custoMaterial = (gramas / 1000.0) * filamento.getPrecoKg();
            double consumoKWh = (minutos / 60.0) * impressora.getConsumoWatts()/1000.0;
            double custoEnergia = consumoKWh * impressora.getValorKwH();
            double custoTotal = custoMaterial + custoEnergia;
            double precoFinal = custoTotal * (1 + lucro / 100.0);
            Venda venda = new Venda(new Cliente(clienteId, ""), filamento, status, gramas, minutos, precoFinal);
            vendaDAO.novaVenda(venda);

            exchange.getResponseHeaders().set("Location", "/vendas");
            exchange.sendResponseHeaders(303, -1);
        } catch (Exception e) { throw new IOException(e); }
    }
}
