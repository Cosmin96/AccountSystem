package service;

import model.Account;

import java.util.List;

public interface AccountService {
    public List<Account> getAccount(Long accountId) throws Exception;
    public List<Account> getAccounts(Long userId) throws Exception;
    public void addAccount(Account account) throws Exception;
}
