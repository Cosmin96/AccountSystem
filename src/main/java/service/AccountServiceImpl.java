package service;

import exception.CustomException;
import model.*;
import repository.AccountRepository;
import repository.TransactionRepository;

import javax.ws.rs.core.Response;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    public AccountServiceImpl() { }

    public List<Account> getAccount(Long accountId) throws CustomException {
        return new AccountRepository().getAccount(accountId);
    }


    public List<Account> getAccounts(Long userId) throws CustomException {
        return new AccountRepository().getAccounts(userId);
    }

    public List<Transaction> getTransaction(Long transactionId) throws CustomException {
        return new TransactionRepository().getTransaction(transactionId);
    }

    public List<Transaction> getAllTransactions() throws CustomException {
        return new TransactionRepository().getAllTransactions();
    }

    public void saveTransaction(Transaction transaction) throws CustomException {
        new TransactionRepository().saveTransaction(transaction);
    }

    public void addAccount(Account account) throws CustomException {
        new AccountRepository().addAccount(account);
    }

    public void withdrawMoney(Account account, Withdrawal transaction) throws CustomException {
        if (transaction.getAmount() > account.getBalance()) {
            throw new CustomException(Response.Status.FORBIDDEN, "Withdrawal not possible due to insufficient funds");
        }
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Withdrawal not possible due to currency mismatch");
        }
        updateAccount(account.getId(), account.getBalance() - transaction.getAmount());
        saveTransaction(transaction);
    }

    public void depositMoney(Account account, Deposit transaction) throws CustomException {
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            account = lookForCorrectAccount(account.getOwnerId(), transaction.getCurrency());
        }
        updateAccount(account.getId(), account.getBalance() + transaction.getAmount());
        saveTransaction(transaction);
    }

    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) throws CustomException {
        if (transaction.getAmount() > fromAccount.getBalance()) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible due to insufficient funds");
        }
        if (!transaction.getCurrency().equals(fromAccount.getCurrency())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible due to currency mismatch");
        }
        if (!transaction.getCurrency().equals(toAccount.getCurrency())) {
            toAccount = lookForCorrectAccount(toAccount.getOwnerId(), transaction.getCurrency());
        }
        saveTransaction(transaction);
        updateAccount(fromAccount.getId(), fromAccount.getBalance() - transaction.getAmount());
        updateAccount(toAccount.getId(), toAccount.getBalance() + transaction.getAmount());
    }

    private void updateAccount(Long id, Double amount) throws CustomException {
        new AccountRepository().updateAccount(id, amount);
    }

    private Account lookForCorrectAccount(Long id, String currency) throws CustomException {
        for (Account account: getAccounts(id)) {
            if (account.getCurrency().equals(currency)) {
                return account;
            }
        }
        Account account = new Account(0d, id, currency);
        addAccount(account);
        return account;
    }
}
