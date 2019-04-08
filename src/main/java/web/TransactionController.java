package web;

import exception.CustomException;
import model.*;
import service.AccountService;
import service.AccountServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transaction")
public class TransactionController {

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getTransactions(@PathParam("id") Long id) throws CustomException {
        return new AccountServiceImpl().getTransaction(id);
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAllTransactions() throws CustomException {
        return new AccountServiceImpl().getAllTransactions();
    }

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response withdrawMoney(Withdrawal transaction) throws CustomException {
        AccountService accountService = new AccountServiceImpl();
        List<Account> accounts = accountService.getAccount(transaction.getFromAccount());

        if(accounts.size() == 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Withdrawal not possible because account does not exist");
        }

        Account account = accounts.get(0);
        accountService.withdrawMoney(account, transaction);
        return Response.ok().entity(
                new CustomResponse("Withdrawal from account " + account.getId() + " was successful")
        ).build();
    }

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response depositMoney(Deposit transaction) throws CustomException {
        AccountService accountService = new AccountServiceImpl();
        List<Account> accounts = accountService.getAccount(transaction.getToAccount());

        if(accounts.size() == 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Deposit not possible because account does not exist");
        }

        Account account = accounts.get(0);
        accountService.depositMoney(account, transaction);
        return Response.ok().entity(
                new CustomResponse("Deposit to account " + account.getId() + " was successful")
        ).build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferMoney(Transfer transaction) throws CustomException {
        AccountService accountService = new AccountServiceImpl();
        List<Account> fromAccounts = accountService.getAccount(transaction.getFromAccount());
        List<Account> toAccounts = accountService.getAccount(transaction.getToAccount());

        if(fromAccounts.size() == 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transfer not possible because withdrawal account does not exist");
        }
        if(toAccounts.size() == 0) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Transfer not possible because deposit account does not exist");
        }

        Account fromAccount = fromAccounts.get(0);
        Account toAccount = toAccounts.get(0);

        accountService.transferMoney(fromAccount, toAccount, transaction);

        return Response.ok().entity(
                new CustomResponse("Transfer from account " + fromAccount.getId() + " was successful")
        ).build();
    }
}
