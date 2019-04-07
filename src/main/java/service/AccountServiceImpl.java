package service;

import model.*;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    public AccountServiceImpl() { }

    public List<Account> getAccount(Long accountId) throws Exception {
        return new AccountRepository().getAccount(accountId);
    }


    public List<Account> getAccounts(Long userId) throws Exception {
        return new AccountRepository().getAccounts(userId);
    }

    public List<Transaction> getTransaction(Long transactionId) throws Exception {
        return new TransactionRepository().getTransaction(transactionId);
    }

    public List<Transaction> getAllTransactions() throws Exception {
        return new TransactionRepository().getAllTransactions();
    }

    public void saveTransaction(Transaction transaction) throws Exception {
        new TransactionRepository().saveTransaction(transaction);
    }

    public void addAccount(Account account) throws Exception {
        new AccountRepository().addAccount(account);
    }

    public void updateAccount(Long id, Double amount) throws Exception {
        new AccountRepository().updateAccount(id, amount);
    }

    public void withdrawMoney(Account account, Withdrawal transaction) throws Exception {
        if (transaction.getAmount() > account.getBalance()) {
            throw new Exception("Withdrawal not possible");
        }
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            throw new Exception("Withdrawal not possible due to currency mismatch");
        }
        updateAccount(account.getId(), account.getBalance() - transaction.getAmount());
        saveTransaction(transaction);
    }

    public void depositMoney(Account account, Deposit transaction) throws Exception {
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            throw new Exception("Deposit not possible due to currency mismatch");
        }
       updateAccount(account.getId(), account.getBalance() + transaction.getAmount());
        saveTransaction(transaction);
    }

    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) throws  Exception {
        if (transaction.getAmount() > fromAccount.getBalance()) {
            throw new Exception("Withdrawal not possible");
        }
        if (!transaction.getCurrency().equals(fromAccount.getCurrency())) {
            throw new Exception("Transfer not possible due to currency mismatch");
        }
        if (!transaction.getCurrency().equals(toAccount.getCurrency())) {
            toAccount = lookForCorrectAccount(toAccount.getOwnerId(), transaction.getCurrency());
            addAccount(toAccount);
        }
        updateAccount(fromAccount.getId(), fromAccount.getBalance() - transaction.getAmount());
        updateAccount(toAccount.getId(), toAccount.getBalance() + transaction.getAmount());
        saveTransaction(transaction);
    }

    private Account lookForCorrectAccount(Long id, String currency) throws Exception {
        for (Account account: getAccounts(id)) {
            if (account.getCurrency().equals(currency)) {
                return account;
            }
        }
        return new Account(0d, id, currency);
    }
}
