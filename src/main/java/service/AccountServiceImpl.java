package service;

import model.Account;
import repository.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    public AccountServiceImpl() { }

    public List<Account> getAccount(Long accountId) throws Exception {
        return new AccountRepository().getAccount(accountId);
    }


    public List<Account> getAccounts(Long userId) throws Exception {
        return new AccountRepository().getAccounts(userId);
    }

    public void addAccount(Account account) throws Exception {
        new AccountRepository().addAccount(account);
    }
}
