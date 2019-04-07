package web;

import model.Account;
import model.Deposit;
import model.Transfer;
import model.Withdrawal;
import service.AccountService;
import service.AccountServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
public class TransactionController {

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    public void withdrawMoney(Withdrawal transaction) throws Exception {
        AccountService accountService = new AccountServiceImpl();
        Account account = accountService.getAccount(transaction.getFromAccount()).get(0);
        accountService.withdrawMoney(account, transaction);
    }

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    public void depositMoney(Deposit transaction) throws Exception {
        AccountService accountService = new AccountServiceImpl();
        Account account = accountService.getAccount(transaction.getToAccount()).get(0);
        accountService.depositMoney(account, transaction);
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public void transferMoney(Transfer transaction) throws Exception {
        AccountService accountService = new AccountServiceImpl();
        Account fromAccount = accountService.getAccount(transaction.getFromAccount()).get(0);
        Account toAccount = accountService.getAccount(transaction.getToAccount()).get(0);

        accountService.transferMoney(fromAccount, toAccount, transaction);
    }
}
