package src.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
            <html><head><title>Gestao3D</title></head><body>
            <h1>Sistema de Gestao 3D</h1>
            <ul>
            <li><a href='/clientes'>Clientes</a></li>
            <li><a href='/filamentos'>Filamentos</a></li>
            <li><a href='/impressoras'>Impressoras</a></li>
            <li><a href='/vendas'>Registrar Venda</a></li>
            <li><a href='/relatorio'>Relatorio de Vendas</a></li>
            </ul>
            </body></html>
            """;
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
