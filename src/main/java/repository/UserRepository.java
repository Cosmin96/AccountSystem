package repository;

import config.Configuration;
import exception.CustomException;
import model.User;
import org.apache.commons.dbutils.DbUtils;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> getAllUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_ALL_USERS"));
            rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getLong("Id"), rs.getString("Username"), rs.getString("FirstName"), rs.getString("LastName")));
            }
            return users;
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "User query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public User getUser(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_USER"));
            stmt.setString(1, id.toString());
            rs = stmt.executeQuery();
            while(rs.next()) {
                user = new User(rs.getLong("Id"), rs.getString("Username"), rs.getString("FirstName"), rs.getString("LastName"));
            }
            if (user == null) {
                throw new CustomException(Response.Status.NOT_FOUND, "User with id " + id + " could not be found");
            }
            return user;
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "User query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public Long addUser(User user) {
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
                throw new CustomException(Response.Status.BAD_REQUEST, "User not created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new CustomException(Response.Status.BAD_REQUEST, "User not created");
            }
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "User not created");
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }
}
