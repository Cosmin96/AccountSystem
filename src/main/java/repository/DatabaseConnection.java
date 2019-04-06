package repository;


import config.Configuration;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public DatabaseConnection() {
        DbUtils.loadDriver(Configuration.getStringProperty("h2_driver"));
    }

    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(
                Configuration.getStringProperty("h2_connection_url"),
                Configuration.getStringProperty("h2_user"),
                Configuration.getStringProperty("h2_password")
        );
    }
}
