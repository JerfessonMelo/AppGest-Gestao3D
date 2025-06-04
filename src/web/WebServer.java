package src.web;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new IndexHandler());
        server.createContext("/clientes", new ClienteHandler());
        server.createContext("/filamentos", new FilamentoHandler());
        server.createContext("/impressoras", new ImpressoraHandler());
        server.createContext("/vendas", new VendaHandler());
        server.createContext("/relatorio", new RelatorioHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Servidor web iniciado na porta 8080");
    }
}
