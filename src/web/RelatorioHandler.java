package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import src.dao.VendaDAO;
import src.model.VendaDetalhada;

public class RelatorioHandler implements HttpHandler {
    private final VendaDAO dao = new VendaDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        try {
            List<VendaDetalhada> vendas = dao.relatorioVendas();
            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h2>Relatorio de Vendas</h2>");
            html.append("<table border='1'><tr><th>ID</th><th>Cliente</th><th>Filamento</th><th>Gramas</th><th>Minutos</th><th>Total</th><th>Status</th></tr>");
            for (VendaDetalhada v : vendas) {
                html.append("<tr><td>").append(v.getId()).append("</td><td>")
                    .append(v.getCliente()).append("</td><td>")
                    .append(v.getFilamento()).append("</td><td>")
                    .append(String.format("%.1f", v.getgramasUtilizadas())).append("</td><td>")
                    .append(v.getMinutos()).append("</td><td>")
                    .append(String.format("%.2f", v.getPrecoTotal())).append("</td><td>")
                    .append(v.getStatusPagamento()).append("</td></tr>");
            }
            html.append("</table><p><a href='/'>Voltar</a></p></body></html>");
            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
        } catch (Exception e) {
            String msg = "Erro interno";
            exchange.sendResponseHeaders(500, msg.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(msg.getBytes(StandardCharsets.UTF_8)); }
        }
    }
}
