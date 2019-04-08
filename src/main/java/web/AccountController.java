package web;

import exception.CustomException;
import model.Account;
import model.CustomResponse;
import service.AccountServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/account")
public class AccountController {


    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccountsForUser(@PathParam("id") Long id) throws CustomException {
        return new AccountServiceImpl().getAccounts(id);
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccount(@PathParam("id") Long id) throws CustomException {
        return new AccountServiceImpl().getAccount(id);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAccount(Account account) throws CustomException {
        new AccountServiceImpl().addAccount(account);
        return Response.ok().entity(
                new CustomResponse("Account was added successfully to user " + account.getId())
        ).build();
    }
}
