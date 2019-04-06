package web;

import model.Account;
import model.User;
import service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccountsForUser(@PathParam("id") Long id) throws Exception {
        return this.accountService.getAccounts(id);
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccount(@PathParam("id") Long id) throws Exception {
        return this.accountService.getAccount(id);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAccount(String currency, Long ownerId) throws Exception {
        this.accountService.addAccount(currency, ownerId);
        return "Account added successfully";
    }
}
