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

    public List<Account> getAccount(Long accountId) {
        return accountRepository.getAccount(accountId);
    }


    public List<Account> getAccounts(Long userId) {
        return accountRepository.getAccounts(userId);
    }

    public List<Transaction> getTransaction(Long transactionId) {
        return transactionRepository.getTransaction(transactionId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public Long addAccount(Account account) {
        if(userRepository.getUser(account.getOwnerId()).size() == 0) {
            throw new CustomException(Response.Status.FORBIDDEN, "Account cannot be created because user does not exist");
        }
        return accountRepository.addAccount(account);
    }

    public void withdrawMoney(Account account, Withdrawal transaction) {
        if (transaction.getAmount() <= 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Withdrawal not possible because amount cannot be less than zero");
        }
        if (!transaction.getType().equals("Withdrawal")) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction type must be Withdrawal");
        }
        if (transaction.getAmount() > account.getBalance()) {
            throw new CustomException(Response.Status.FORBIDDEN, "Withdrawal not possible due to insufficient funds");
        }
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Withdrawal not possible due to currency mismatch");
        }
        if (verifyDigits(transaction.getAmount())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer amount cannot have more than 2 decimal places");
        }
        updateAccount(account.getId(), Math.round((account.getBalance() - transaction.getAmount()) * 100) / 100d);
        saveTransaction(transaction);
    }

    public void depositMoney(Account account, Deposit transaction) {
        if (transaction.getAmount() <= 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Deposit not possible because amount cannot be less than zero");
        }
        if (!transaction.getType().equals("Deposit")) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction type must be Deposit");
        }
        if (!transaction.getCurrency().equals(account.getCurrency())) {
            account = lookForCorrectAccount(account.getOwnerId(), transaction.getCurrency());
        }
        if (verifyDigits(transaction.getAmount())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer amount cannot have more than 2 decimal places");
        }
        updateAccount(account.getId(), Math.round((account.getBalance() + transaction.getAmount()) * 100) / 100d);
        saveTransaction(transaction);
    }

    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) {
        if (transaction.getFromAccount().equals(transaction.getToAccount())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible from account to itself");
        }
        if (transaction.getAmount() <= 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transfer not possible because amount cannot be less than zero");
        }
        if (!transaction.getType().equals("Transfer")) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transaction type must be Transfer");
        }
        if (transaction.getAmount() > fromAccount.getBalance()) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible due to insufficient funds");
        }
        if (verifyDigits(transaction.getAmount())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer amount cannot have more than 2 decimal places");
        }
        if (fromAccount.getOwnerId().equals(toAccount.getOwnerId()) && !fromAccount.getCurrency().equals(toAccount.getCurrency()))  {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible because exchange between currencies is not allowed");
        }
        if (!transaction.getCurrency().equals(fromAccount.getCurrency())) {
            throw new CustomException(Response.Status.FORBIDDEN, "Transfer not possible due to currency mismatch");
        }
        if (!transaction.getCurrency().equals(toAccount.getCurrency())) {
            toAccount = lookForCorrectAccount(toAccount.getOwnerId(), transaction.getCurrency());
        }
        saveTransaction(transaction);
        updateAccount(fromAccount.getId(), Math.round((fromAccount.getBalance() - transaction.getAmount()) * 100) / 100d);
        updateAccount(toAccount.getId(), Math.round((toAccount.getBalance() + transaction.getAmount()) * 100) / 100d);
    }

    private boolean verifyDigits(Double amount) {
        String amountValue = Double.toString(Math.abs(amount));
        if(!amountValue.contains(".")) {
            return false;
        }
        int integerPlaces = amountValue.indexOf('.');
        int decimalPlaces = amountValue.length() - integerPlaces - 1;
        return decimalPlaces > 2;
    }

    private void updateAccount(Long id, Double amount) {
        accountRepository.updateAccount(id, amount);
    }

    private Account lookForCorrectAccount(Long id, String currency) {
        for (Account account: getAccounts(id)) {
            if (account.getCurrency().equals(currency)) {
                return account;
            }
        }
        Account account = new Account(0d, id, currency);
        account.setId(addAccount(account));
        return account;
    }
}
