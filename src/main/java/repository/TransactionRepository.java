package repository;

import config.Configuration;
import model.Deposit;
import model.Transaction;
import model.Transfer;
import model.Withdrawal;
import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public List<Transaction> getTransaction(Long transactionId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_TRANSACTION"));
            stmt.setString(1, transactionId.toString());
            rs = stmt.executeQuery();
            return queryForTransaction(rs, transactions);
        } catch (SQLException e) {
            throw new Exception("Error reading transaction", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public List<Transaction> getAllTransactions() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = conn.prepareStatement(Configuration.getStringProperty("GET_ALL_TRANSACTIONS"));
            rs = stmt.executeQuery();
            return queryForTransaction(rs, transactions);
        } catch (SQLException e) {
            throw new Exception("Error reading transaction", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }

    public Long saveTransaction(Transaction transaction) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = DatabaseConnection.getDBConnection();
            stmt = prepareQuery(transaction, conn);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Transaction not created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new Exception("Transaction not created");
            }
        } catch (SQLException e) {
            throw new Exception("Error creating Transaction", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }

    private List<Transaction> queryForTransaction(ResultSet rs, List<Transaction> transactions) throws Exception {
        while (rs.next()) {
            String type = rs.getString("Type");
            Transaction transaction = null;
            if(type.equals("Deposit")) {
                transaction = new Deposit(rs.getLong("ToAccount"), rs.getDouble("Amount"), rs.getString("Currency"));
            } else if(type.equals("Withdrawal")) {
                transaction = new Withdrawal(rs.getLong("FromAccount"), rs.getDouble("Amount"), rs.getString("Currency"));
            } else if(type.equals("Transfer")){
                transaction = new Transfer(rs.getLong("FromAccount"), rs.getLong("ToAccount"), rs.getDouble("Amount"), rs.getString("Currency"));
            }
            transactions.add(transaction);
        }
        return transactions;
    }

    private PreparedStatement prepareQuery(Transaction transaction, Connection conn) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(Configuration.getStringProperty("ADD_TRANSACTION"), Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, transaction.getType());
        stmt.setDouble(4, transaction.getAmount());
        stmt.setString(5, transaction.getCurrency());

        if(transaction.getType().equals("Deposit")) {
            stmt.setLong(2, -1);
            stmt.setLong(3, ((Deposit) transaction).getToAccount());
        } else if(transaction.getType().equals("Withdrawal")) {
            stmt.setLong(2, ((Withdrawal) transaction).getFromAccount());
            stmt.setLong(3, -1);
        } else if(transaction.getType().equals("Transfer")){
            stmt.setLong(2, ((Transfer) transaction).getFromAccount());
            stmt.setLong(3, ((Transfer) transaction).getToAccount());
        } else {
            stmt.setLong(2, -1);
            stmt.setLong(3, -1);
        }
        return stmt;
    }
}
