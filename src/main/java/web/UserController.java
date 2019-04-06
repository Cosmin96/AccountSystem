package web;

import jdk.nashorn.internal.objects.NativeJSON;
import model.User;
import repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
public class UserController {

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> available(@PathParam("id") Long id) throws Exception {
        UserRepository userRepo = new UserRepository();
        if(id != null) {
            return userRepo.getUser(id);
        }
        return userRepo.getAllUsers();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(User user) throws Exception {
        UserRepository userRepo = new UserRepository();
        userRepo.addUser(user);
        return "User added successfully";
    }
}
