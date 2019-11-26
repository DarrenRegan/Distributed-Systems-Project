package ie.gmit.ds;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource {
    private HashMap<Integer, User> usersMap = new HashMap<>();

    public UserApiResource(){
        User test1 = new User(1, "Test", "darren123@gmail.com", "123darren123");
        User test2 = new User(2, "Test2", "john123@gmail.com", "123john123");
        usersMap.put(test1.getUserId(), test1);
        usersMap.put(test2.getUserId(), test2);
    }

    @GET
    public Collection<User> getUsers(){
        return usersMap.values();
    }

    @GET
    @Path("{userID}")
    public User getUserById(@PathParam("userId") int userId){
        return usersMap.get(userId);
    }

    @Path("/signUp")
    @POST
    public Response createUser(User user){
        User user1 = usersMap.get(user.getUserId());
        return null;
    }

    @DELETE
    @Path("delete/{userId}")
    public Response deleteUser(@PathParam("UserId") Integer userId) {
        User user = usersMap.get(userId);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            usersMap.remove(userId);
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
