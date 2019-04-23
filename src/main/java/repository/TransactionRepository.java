package repository;

import config.Configuration;
import exception.CustomException;
import model.Deposit;
import model.Transaction;
import model.Transfer;
import model.Withdrawal;
import org.apache.commons.dbutils.DbUtils;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public Transaction getTransaction(Long transactionId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Transaction transaction = null;
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_TRANSACTION"));
            stmt.setString(1, transactionId.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                transaction = queryForTransaction(rs);
            }
            if(transaction == null) {
                throw new CustomException(Response.Status.NOT_FOUND, "Transaction with id " + transactionId + " was not found");
            }
            return transaction;
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public List<Transaction> getAllTransactions() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_ALL_TRANSACTIONS"));
            rs = stmt.executeQuery();
            while(rs.next()) {
                transactions.add(queryForTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction query failed");
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public Long saveTransaction(Transaction transaction) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("ADD_TRANSACTION"), Statement.RETURN_GENERATED_KEYS);
            prepareQuery(transaction, stmt);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CustomException(Response.Status.BAD_REQUEST, "Transaction not created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new CustomException(Response.Status.BAD_REQUEST, "Transaction not created");
            }
        } catch (SQLException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction not created");
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }

    private Transaction queryForTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = null;
        String type = rs.getString("Type");
        if (type.equals("Deposit")) {
            transaction = new Deposit(
                    rs.getLong("Id"),
                    rs.getLong("ToAccount"),
                    rs.getDouble("Amount"),
                    rs.getString("Currency")
            );
        } else if (type.equals("Withdrawal")) {
            transaction = new Withdrawal(
                    rs.getLong("Id"),
                    rs.getLong("FromAccount"),
                    rs.getDouble("Amount"),
                    rs.getString("Currency")
            );
        } else if (type.equals("Transfer")) {
            transaction = new Transfer(
                    rs.getLong("Id"),
                    rs.getLong("FromAccount"),
                    rs.getLong("ToAccount"),
                    rs.getDouble("Amount"),
                    rs.getString("Currency")
            );
        }
        return transaction;
    }

    private void prepareQuery(Transaction transaction, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, transaction.getType());
        stmt.setDouble(4, transaction.getAmount());
        stmt.setString(5, transaction.getCurrency());

        if (transaction.getType().equals("Deposit")) {
            stmt.setLong(2, -1);
            stmt.setLong(3, ((Deposit) transaction).getToAccount());
        } else if (transaction.getType().equals("Withdrawal")) {
            stmt.setLong(2, ((Withdrawal) transaction).getFromAccount());
            stmt.setLong(3, -1);
        } else if (transaction.getType().equals("Transfer")) {
            stmt.setLong(2, ((Transfer) transaction).getFromAccount());
            stmt.setLong(3, ((Transfer) transaction).getToAccount());
        } else {
            stmt.setLong(2, -1);
            stmt.setLong(3, -1);
        }
    }
}
