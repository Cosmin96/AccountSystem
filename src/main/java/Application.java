import org.apache.commons.dbutils.DbUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.h2.tools.RunScript;
import repository.DatabaseConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws Exception {
        setupDB();
        startServer();
    }

    private static void startServer() throws Exception {
        Server server = new Server(8080);

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages", "web");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            server.destroy();
        }
    }

    private static void setupDB() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            String setupFile = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("dbsetup.sql")
                    .getFile();
            RunScript.execute(conn, new FileReader(setupFile));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
