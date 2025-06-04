package src.web;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/clientes", new ClienteHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Servidor web iniciado na porta 8080");
    }
}
