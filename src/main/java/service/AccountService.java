package service;

import model.*;

import java.util.List;

public interface AccountService {
    public Account getAccount(Long accountId);
    public List<Account> getAccountsForUser(Long userId);
    public Transaction getTransaction(Long transactionId);
    public List<Transaction> getAllTransactions() ;
    public void saveTransaction(Transaction transaction);
    public Long addAccount(Account account);
    public void withdrawMoney(Account account, Withdrawal transaction);
    public void depositMoney(Account account, Deposit transaction);
    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction);
}
