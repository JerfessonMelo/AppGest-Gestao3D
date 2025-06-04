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
import src.model.Cliente;

public class ClienteHandler implements HttpHandler {
    private final ClienteDAO dao = new ClienteDAO();

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
            List<Cliente> clientes = dao.listaClientes();
            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h2>Cadastrar Cliente</h2>");
            html.append("<form method='POST'>Nome: <input name='nome'><input type='submit' value='Salvar'></form>");
            html.append("<h2>Clientes</h2><table border='1'><tr><th>ID</th><th>Nome</th></tr>");
            for (Cliente c : clientes) {
                html.append("<tr><td>").append(c.getId()).append("</td><td>")
                    .append(c.getNome()).append("</td></tr>");
            }
            html.append("</table><p><a href='/'>Voltar</a></p></body></html>");
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
            String nome = "";
            for (String p : body.split("&")) {
                String[] kv = p.split("=");
                if (kv.length == 2 && kv[0].equals("nome")) {
                    nome = kv[1];
                }
            }
            if (!nome.isBlank()) {
                dao.cadastrarCliente(new Cliente(nome));
            }
            exchange.getResponseHeaders().set("Location", "/clientes");
            exchange.sendResponseHeaders(303, -1);
        } catch (Exception e) { throw new IOException(e); }
    }
}
