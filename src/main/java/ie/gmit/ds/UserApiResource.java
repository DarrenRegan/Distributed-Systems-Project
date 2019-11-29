package ie.gmit.ds;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

@Path("/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserApiResource {
    private HashMap<Integer, User> usersMap = new HashMap<>();
    private PasswordClient client;
    private PasswordServiceImpl clientImpl;
    private Passwords clientPass;
    private Scanner console;
    private int port;

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
    @Path("{userId}")
    public User getUserById(@PathParam("userId") int userId){
        return usersMap.get(userId);
    }

    @PUT
    @Path("update")
    public Response updateUser(User user){

        if(usersMap.containsKey(user.getUserId())){
            usersMap.remove(user.getUserId());

            //clientImpl.hash(user.getUserId(), user.getPassword());

            String result = "User Updated: " + user.getUserId();
            return Response.status(200).entity(result).build();

        }else{
            String print = "User Does Not Exist: " + user.getUserId();
            return Response.status(404).entity(print).build();
        }
    }

    @POST
    @Path("/addUser")
    public Response createUser(User user) throws URISyntaxException {

        //String hash = new String(String.valueOf(client.hashCode()));
        //String salt = new String(String.valueOf());
        //PasswordServiceImpl hashedUser = new PasswordServiceImpl(user.getUserId(), user.getUserName(), user.getEmail(), hash, salt);
        //usersMap.put();

        String print = "Added User: " + user.getUserId();
        return Response.created(new URI("/users/" + user.getUserId())).build();
       // return Response.status(200).entity(print).build();
    }

    @DELETE
    @Path("delete/{userId}")
    public Response deleteUser(@PathParam("userId") Integer userId) {
        //User user = usersMap.get(userId);
        if (usersMap.containsKey(userId)){
            usersMap.remove(userId);
            String print = ("Removed User: " + userId + "from DB");
            return Response.status(200).entity(print).build();
        }else {
            String print = ("User Does Not Exist: " + userId);
            return Response.status(404).entity(print).build();
        }
    }
}
