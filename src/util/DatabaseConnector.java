package src.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DB_NAME = "Banco3D.db";
    private static final String APP_FOLDER = "BdSQ";

    public Connection connect() {
        try {
            Path destino = Paths.get(System.getProperty("user.home"), APP_FOLDER, DB_NAME);

            if (Files.notExists(destino)) {
                InputStream input = getClass().getResourceAsStream("/" + DB_NAME);
                if (input == null) {
                    throw new FileNotFoundException("Banco não encontrado nos resources.");
                }

                Files.createDirectories(destino.getParent());
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Banco copiado para: " + destino);
            }

            String dbUrl = "jdbc:sqlite:" + destino.toString();
            Connection conn = DriverManager.getConnection(dbUrl);
            try (var stmt = conn.createStatement()){
                stmt.execute("PRAGMA foreign_keys = ON");
            }
            System.out.println("Conectado ao banco externo: " + dbUrl);
            return conn;

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o banco: " + e.getMessage());
            return null;
        }
    }
}


