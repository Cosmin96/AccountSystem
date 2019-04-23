package web;

import exception.CustomException;
import model.*;
import service.AccountService;
import service.AccountServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transaction")
public class TransactionController {

    @Inject
    AccountService accountService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction getTransactions(@PathParam("id") Long id) {
        if(id == null) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Please provide a valid transaction ID");
        }
        return accountService.getTransaction(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAllTransactions() {
        return accountService.getAllTransactions();
    }

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response withdrawMoney(Withdrawal transaction) {
        accountService.withdrawMoney(accountService.getAccount(transaction.getFromAccount()), transaction);
        return Response.ok().entity(
                new CustomResponse("Withdrawal from account " + transaction.getFromAccount() + " was successful")
        ).build();
    }

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response depositMoney(Deposit transaction) {
        accountService.depositMoney(accountService.getAccount(transaction.getToAccount()), transaction);
        return Response.ok().entity(
                new CustomResponse("Deposit to account " + transaction.getToAccount() + " was successful")
        ).build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferMoney(Transfer transaction) {
        accountService.transferMoney(
                accountService.getAccount(transaction.getFromAccount()),
                accountService.getAccount(transaction.getToAccount()),
                transaction);

        return Response.ok().entity(
                new CustomResponse("Transfer from account " + transaction.getFromAccount() + " was successful")
        ).build();
    }
}
