package service;

import exception.CustomException;
import model.*;

import java.util.List;

public interface AccountService {
    public List<Account> getAccount(Long accountId) throws CustomException;
    public List<Account> getAccounts(Long userId) throws CustomException;
    public List<Transaction> getTransaction(Long transactionId) throws CustomException;
    public List<Transaction> getAllTransactions() throws CustomException;
    public void saveTransaction(Transaction transaction) throws CustomException;
    public void addAccount(Account account) throws CustomException;
    public void withdrawMoney(Account account, Withdrawal transaction) throws CustomException;
    public void depositMoney(Account account, Deposit transaction) throws CustomException;
    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) throws CustomException;
}
