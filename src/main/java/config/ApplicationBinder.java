package config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.AccountService;
import service.AccountServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserServiceImpl.class).to(UserService.class);
        bind(AccountServiceImpl.class).to(AccountService.class);
        bind(UserRepository.class).to(UserRepository.class);
        bind(AccountRepository.class).to(AccountRepository.class);
        bind(TransactionRepository.class).to(TransactionRepository.class);
    }
}
