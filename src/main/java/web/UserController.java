package web;

import model.User;
import service.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserController {

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@PathParam("id") Long id) throws Exception {
        return new UserServiceImpl().getUser(id);
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() throws Exception {
        return new UserServiceImpl().getAllUsers();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(User user) throws Exception {
        new UserServiceImpl().addUser(user);
        return "User added successfully";
    }
}
