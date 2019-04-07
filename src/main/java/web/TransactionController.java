package web;

import model.*;
import service.AccountService;
import service.AccountServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transaction")
public class TransactionController {

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getTransactions(@PathParam("id") Long id) throws Exception {
        if(id != null) {
            return new AccountServiceImpl().getAllTransactions();
        }
        return new AccountServiceImpl().getTransaction(id);
    }

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
