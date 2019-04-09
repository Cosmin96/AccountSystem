import config.ApplicationBinder;
import exception.CustomException;
import org.apache.commons.dbutils.DbUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.internal.inject.Custom;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.h2.tools.RunScript;
import repository.DatabaseConnection;

import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        setupDB();
        startServer();
    }

    private static void startServer() throws CustomException {
        ResourceConfig config = new ResourceConfig();
        config.packages("web");
        config.register(new ApplicationBinder());
        ServletHolder jerseyServlet
                = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8080);
        ServletContextHandler context
                = new ServletContextHandler(server, "/");
        context.addServlet(jerseyServlet, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR, "Server could not be started");
        } finally {
            server.destroy();
        }
    }

    private static void setupDB() throws CustomException {
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
}
