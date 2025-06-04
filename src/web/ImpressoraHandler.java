package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import src.dao.ImpressoraDAO;
import src.model.Impressora;

public class ImpressoraHandler implements HttpHandler {
    private final ImpressoraDAO dao = new ImpressoraDAO();

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
            List<Impressora> lista = dao.listaImpressoras();
            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h2>Cadastrar Impressora</h2>");
            html.append("<form method='POST'>");
            html.append("Nome: <input name='nome'><br>");
            html.append("ConsumoWatts: <input name='consumo' type='number' step='0.1'><br>");
            html.append("ValorKwH: <input name='valorkwh' type='number' step='0.01'><br>");
            html.append("<input type='submit' value='Salvar'>");
            html.append("</form>");
            html.append("<h2>Impressoras</h2><table border='1'><tr><th>ID</th><th>Nome</th><th>ConsumoW</th><th>Valor kWh</th></tr>");
            for (Impressora i : lista) {
                html.append("<tr><td>").append(i.getId()).append("</td><td>")
                    .append(i.getNome()).append("</td><td>")
                    .append(i.getConsumoWatts()).append("</td><td>")
                    .append(i.getValorKwH()).append("</td></tr>");
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
            String[] parts = body.split("&");
            String nome = ""; double consumo=0; double valor=0;
            for (String p : parts) {
                String[] kv = p.split("=");
                if (kv.length==2) {
                    switch(kv[0]) {
                        case "nome" -> nome=kv[1];
                        case "consumo" -> { try { consumo = Double.parseDouble(kv[1]); } catch(Exception ex){} }
                        case "valorkwh" -> { try { valor = Double.parseDouble(kv[1]); } catch(Exception ex){} }
                    }
                }
            }
            if (!nome.isBlank()) {
                dao.cadastrarImpressora(new Impressora(0, nome, consumo, valor));
            }
            exchange.getResponseHeaders().set("Location", "/impressoras");
            exchange.sendResponseHeaders(303, -1);
        } catch (Exception e) { throw new IOException(e); }
    }
}
