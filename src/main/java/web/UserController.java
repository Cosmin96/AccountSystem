package web;

import exception.CustomException;
import model.CustomResponse;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@PathParam("id") Long id) throws CustomException {
        return userService.getUser(id);
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() throws CustomException {
        return userService.getAllUsers();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws CustomException {
        userService.addUser(user);
        return Response.ok().entity(
                new CustomResponse("User was added successfully")
        ).build();
    }
}
