package service;

import exception.CustomException;
import model.*;
import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
public class AccountServiceImpl implements AccountService {

    @Inject
    AccountRepository accountRepository;
    @Inject
    TransactionRepository transactionRepository;
    @Inject
    UserRepository userRepository;

    public Account getAccount(Long accountId) {
        return accountRepository.getAccount(accountId);
    }

    public List<Account> getAccountsForUser(Long userId) {
        return accountRepository.getAccounts(userId);
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.getTransaction(transactionId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public Long addAccount(Account account) {
        userRepository.getUser(account.getOwnerId());
        return accountRepository.addAccount(account);
    }

    public void withdrawMoney(Account account, Withdrawal transaction) {
        updateAccount(account.getId(), Math.round((account.getBalance() - transaction.getAmount()) * 100) / 100d);
        saveTransaction(transaction);
    }

    public void depositMoney(Account account, Deposit transaction) {
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            account = lookForCorrectAccount(account.getOwnerId(), transaction.getCurrency());
        }
        updateAccount(account.getId(), Math.round((account.getBalance() + transaction.getAmount()) * 100) / 100d);
        saveTransaction(transaction);
    }

    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) {
        if (!transaction.getCurrency().equals(toAccount.getCurrency())) {
            toAccount = lookForCorrectAccount(toAccount.getOwnerId(), transaction.getCurrency());
        }
        saveTransaction(transaction);
        updateAccount(fromAccount.getId(), Math.round((fromAccount.getBalance() - transaction.getAmount()) * 100) / 100d);
        updateAccount(toAccount.getId(), Math.round((toAccount.getBalance() + transaction.getAmount()) * 100) / 100d);
    }

    private void updateAccount(Long id, Double amount) {
        accountRepository.updateAccount(id, amount);
    }

    private Account lookForCorrectAccount(Long id, String currency) {
        for (Account account: getAccountsForUser(id)) {
            if (account.getCurrency().equals(currency)) {
                return account;
            }
        }
        Account account = new Account(0d, id, currency);
        account.setId(addAccount(account));
        return account;
    }
}
