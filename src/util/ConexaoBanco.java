package src.util;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBanco {

    private static final String DB_NAME = "Banco3D.db";
    private static final String APP_FOLDER = "Gestao3D";
    private static Connection conexao = null;
    private static final Logger LOGGER = Logger.getLogger(ConexaoBanco.class.getName());

    public static Connection getConnection() throws SQLException {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    return conexao;
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Falha ao verificar estado da conexão: ", e);
            }
        }

        try {
            Path destino = Paths.get(System.getProperty("user.home"), APP_FOLDER, DB_NAME);

            if (Files.notExists(destino)) {
                try (InputStream input = ConexaoBanco.class.getResourceAsStream("/" + DB_NAME)) {
                    if (input == null) {
                        throw new FileNotFoundException("Banco '" + DB_NAME + "' não encontrado nos resources.");
                    }

                    Files.createDirectories(destino.getParent());
                    Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info("Banco copiado para: " + destino.toString());
                }
            }

            String dbUrl = "jdbc:sqlite:" + destino.toString();
            conexao = DriverManager.getConnection(dbUrl);

            try (var stmt = conexao.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            LOGGER.info("Conexão com o banco de dados estabelecida: " + dbUrl);
            return conexao;

        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao conectar com o banco: ", e);
            throw new SQLException("Erro ao conectar com o banco", e);
        }
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                LOGGER.info("Conexão com o banco fechada.");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Erro ao fechar a conexão.", e);
            }
        }
    }
}
