package repository;

import config.Configuration;
import model.User;
import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> getAllUsers() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_ALL_USERS"));
            rs = stmt.executeQuery();
            return queryForUser(rs, users);
        } catch (SQLException e) {
            throw new Exception("Error reading user", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public List<User> getUser(Long id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_USER"));
            stmt.setString(1, id.toString());
            rs = stmt.executeQuery();
            return queryForUser(rs, users);
        } catch (SQLException e) {
            throw new Exception("Error reading user", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public Long addUser(User user) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("ADD_USER"), Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("User not created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new Exception("User not created");
            }
        } catch (SQLException e) {
            throw new Exception("Error creating user", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }

    private List<User> queryForUser(ResultSet rs, List<User> users) throws Exception {
        while (rs.next()) {
            User user = new User(rs.getLong("Id"), rs.getString("Username"), rs.getString("FirstName"), rs.getString("LastName"));
            users.add(user);
        }
        return users;
    }
}
