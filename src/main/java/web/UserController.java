package web;

import exception.CustomException;
import model.CustomResponse;
import model.User;
import service.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserController {

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@PathParam("id") Long id) throws CustomException {
        return new UserServiceImpl().getUser(id);
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() throws CustomException {
        return new UserServiceImpl().getAllUsers();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws CustomException {
        new UserServiceImpl().addUser(user);
        return Response.ok().entity(
                new CustomResponse("User was added successfully")
        ).build();
    }
}
