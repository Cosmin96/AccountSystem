package repository;


import config.Configuration;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String h2_driver = Configuration.getStringProperty("h2_driver");
    private static final String h2_connection_url = Configuration.getStringProperty("h2_connection_url");
    private static final String h2_user = Configuration.getStringProperty("h2_user");
    private static final String h2_password = Configuration.getStringProperty("h2_password");

    public DatabaseConnection() {
        DbUtils.loadDriver(h2_driver);
    }

    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);
    }
}
