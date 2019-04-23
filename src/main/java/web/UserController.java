package web;

import exception.CustomException;
import model.CustomResponse;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUsers(@PathParam("id") Long id) {
        if(id == null) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR, "Please provide a valid user id");
        }
        return userService.getUser(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@Valid User user) {
        userService.addUser(user);
        return Response.ok().entity(
                new CustomResponse("User was added successfully")
        ).build();
    }
}
