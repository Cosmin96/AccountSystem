package service;

import model.Account;
import repository.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccount(Long accountId) throws Exception {
        return this.accountRepository.getAccount(accountId);
    }


    public List<Account> getAccounts(Long userId) throws Exception {
        return this.accountRepository.getAccounts(userId);
    }

    public void addAccount(String currency, Long userId) throws Exception {
        this.accountRepository.addAccount(currency, userId);
    }
}
