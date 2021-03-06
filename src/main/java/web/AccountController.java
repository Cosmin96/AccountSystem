package web;

import exception.CustomException;
import model.Account;
import model.CustomResponse;
import service.AccountService;
import service.AccountServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/account")
public class AccountController {

    @Inject
    AccountService accountService;

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccountsForUser(@PathParam("id") Long id) {
        if(id == null) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Please provide a valid user ID");
        }
        return accountService.getAccountsForUser(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("id") Long id) {
        if(id == null) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Please provide a valid account ID");
        }
        return accountService.getAccount(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAccount(@Valid Account account) {
        accountService.addAccount(account);
        return Response.ok().entity(
                new CustomResponse("Account was added successfully to user " + account.getOwnerId())
        ).build();
    }
}
