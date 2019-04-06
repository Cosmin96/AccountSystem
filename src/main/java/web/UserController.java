package web;

import model.User;
import repository.UserRepository;
import service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@PathParam("id") Long id) throws Exception {
        if(id != null) {
            return this.userService.getUser(id);
        }
        return this.userService.getAllUsers();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(User user) throws Exception {
        this.userService.addUser(user);
        return "User added successfully";
    }
}
