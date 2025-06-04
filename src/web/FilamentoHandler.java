package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import src.dao.FilamentoDAO;
import src.model.Filamento;

public class FilamentoHandler implements HttpHandler {
    private final FilamentoDAO dao = new FilamentoDAO();

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
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(msg.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            List<Filamento> lista = dao.listaFilamentos();
            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h2>Cadastrar Filamento</h2>");
            html.append("<form method='POST'>");
            html.append("Tipo: <select name='tipo'>" +
                        "<option>PLA</option>" +
                        "<option>ABS</option>" +
                        "<option>PETG</option></select><br>");
            html.append("Apelido: <input name='apelido'><br>");
            html.append("PrecoKg: <input name='precoKg' type='number'><br>");
            html.append("<input type='submit' value='Salvar'>");
            html.append("</form>");
            html.append("<h2>Filamentos</h2><table border='1'><tr><th>ID</th><th>Tipo</th><th>PrecoKg</th><th>Densidade</th></tr>");
            for (Filamento f : lista) {
                html.append("<tr><td>").append(f.getId()).append("</td><td>")
                    .append(f.getTipo()).append("</td><td>")
                    .append(f.getPrecoKg()).append("</td><td>")
                    .append(f.getDensidade()).append("</td></tr>");
            }
            html.append("</table><p><a href='/'>Voltar</a></p></body></html>");
            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            body = URLDecoder.decode(body, StandardCharsets.UTF_8);
            String[] parts = body.split("&");
            String tipo = "";
            String apelido = "";
            int precoKg = 0;
            for (String p : parts) {
                String[] kv = p.split("=");
                if (kv.length == 2) {
                    switch (kv[0]) {
                        case "tipo": tipo = kv[1]; break;
                        case "apelido": apelido = kv[1]; break;
                        case "precoKg":
                            try { precoKg = Integer.parseInt(kv[1]); } catch(NumberFormatException ex) { /* ignore */ }
                            break;
                    }
                }
            }
            double densidade = switch(tipo) { case "PLA" -> 1.24; case "ABS" -> 1.04; case "PETG" -> 1.27; default -> 1.2; };
            String nomeConcatenado = tipo + " " + apelido;
            if (!nomeConcatenado.trim().isEmpty() && precoKg > 0) {
                dao.cadastrarFilamento(new Filamento(0, nomeConcatenado, precoKg, densidade));
            }
            exchange.getResponseHeaders().set("Location", "/filamentos");
            exchange.sendResponseHeaders(303, -1);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
