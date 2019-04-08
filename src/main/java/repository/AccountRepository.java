package repository;

import config.Configuration;
import exception.CustomException;
import model.Account;
import org.apache.commons.dbutils.DbUtils;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    public List<Account> getAccount(Long accountId) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<Account>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_ACCOUNT"));
            stmt.setString(1, accountId.toString());
            rs = stmt.executeQuery();
            return queryForAccount(rs, accounts);
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Account query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public List<Account> getAccounts(Long userId) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<Account>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_USER_ACCOUNTS"));
            stmt.setString(1, userId.toString());
            rs = stmt.executeQuery();
            return queryForAccount(rs, accounts);
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Account query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public Long addAccount(Account account) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("ADD_ACCOUNT"), Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, 0);
            stmt.setLong(2, account.getOwnerId());
            stmt.setString(3, account.getCurrency());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CustomException(Response.Status.BAD_REQUEST, "Account not created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new CustomException(Response.Status.BAD_REQUEST, "Account not created");
            }
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Account not created");
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }

    public void updateAccount(Long id, Double balance) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("UPDATE_ACCOUNT"));
            stmt.setDouble(1, balance);
            stmt.setLong(2, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CustomException(Response.Status.BAD_REQUEST, "Account not updated");
            }
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Account not updated");
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(stmt);
        }
    }

    private List<Account> queryForAccount(ResultSet rs, List<Account> accounts) throws SQLException {
        while (rs.next()) {
            Account account = new Account(rs.getLong("Id"), rs.getDouble("Balance"), rs.getLong("OwnerID"), rs.getString("Currency"));
            accounts.add(account);
        }
        return accounts;
    }
}
