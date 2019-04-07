package service;

import model.Account;
import model.Deposit;
import model.Transfer;
import model.Withdrawal;

import java.util.List;

public interface AccountService {
    public List<Account> getAccount(Long accountId) throws Exception;
    public List<Account> getAccounts(Long userId) throws Exception;
    public void addAccount(Account account) throws Exception;
    public void withdrawMoney(Account account, Withdrawal transaction) throws Exception;
    public void depositMoney(Account account, Deposit transaction) throws Exception;
    public void transferMoney(Account fromAccount, Account toAccount, Transfer transaction) throws Exception;
}
