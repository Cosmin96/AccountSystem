package config;

import exception.CustomException;
import org.apache.commons.dbutils.DbUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.h2.tools.RunScript;
import repository.DatabaseConnection;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Configuration {

    private static Properties properties = new Properties();
    private static Server server;

    static {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static String getStringProperty(String key) {
        return properties.getProperty(key);
    }

    public static void startServer() throws Exception {
        ResourceConfig config = new ResourceConfig();
        config.packages("web");
        config.packages("config");
        config.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        config.register(new ApplicationBinder());
        ServletHolder jerseyServlet
                = new ServletHolder(new ServletContainer(config));

        server = new Server(8080);
        ServletContextHandler context
                = new ServletContextHandler(server, "/");
        context.addServlet(jerseyServlet, "/*");

        server.start();
    }

    public static void setupDB() throws CustomException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            String setupFile = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("dbSetup.sql")
                    .getFile();
            RunScript.execute(conn, new FileReader(setupFile));
        } catch (SQLException e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR, "Connection to database could not be established");
        } catch (FileNotFoundException e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR, "SQL setup file could not be found");
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static void stopServer() throws Exception {
        server.stop();
    }
}
