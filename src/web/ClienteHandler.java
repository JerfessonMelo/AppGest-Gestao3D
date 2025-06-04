package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        } catch (Exception e) {
            String msg = "Erro interno";
            exchange.sendResponseHeaders(500, msg.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(msg.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            List<Cliente> clientes = dao.listaClientes();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < clientes.size(); i++) {
                Cliente c = clientes.get(i);
                json.append(String.format("{\"id\":%d,\"nome\":\"%s\"}", c.getId(), c.getNome()));
                if (i < clientes.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
            String nome = parseNome(body);
            if (nome == null || nome.isEmpty()) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }
            Cliente c = new Cliente(0, nome);
            dao.cadastrarCliente(c);
            exchange.sendResponseHeaders(201, -1);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    // Espera um JSON simples: {"nome":"Fulano"}
    private String parseNome(String json) {
        json = json.replace("{", "").replace("}", "").replace("\"", "").trim();
        String[] parts = json.split(":");
        if (parts.length == 2 && parts[0].trim().equals("nome")) {
            return parts[1].trim();
        }
        return null;
    }
}
