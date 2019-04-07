package web;

import model.Account;
import service.AccountServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/account")
public class AccountController {


    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccountsForUser(@PathParam("id") Long id) throws Exception {
        return new AccountServiceImpl().getAccounts(id);
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccount(@PathParam("id") Long id) throws Exception {
        return new AccountServiceImpl().getAccount(id);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAccount(Account account) throws Exception {
        new AccountServiceImpl().addAccount(account);
        return "Account added successfully";
    }
}
