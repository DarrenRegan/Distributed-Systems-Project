package ie.gmit.ds;

import com.google.protobuf.ByteString;
import com.google.rpc.Status;
import javax.validation.ConstraintViolation;
import javax.validation.
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Set;
import javax.ws.rs.Produces;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource {
    private static final Logger logger = Logger.getLogger(UserApiResource.class.getName());
    private final Validator validator;
    PasswordClient pClient = new PasswordClient("localhost", 50551);

    public UserApiResource(Validator validator){
        this.validator = validator;
    }

    @GET
    public Response getUsers(){
        return Response.ok(UserDatabase.getUsers()).build();
    }

    @GET
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Integer userId){
        User user = UserDatabase.getUser(userId);

        if(user != null){
            return Response.ok(user).build();
        } else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{userId")
    public Response updateUser(@PathParam("userId") Integer id, User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User user1 = UserDatabase.getUser(user.getUserId());
        if (violations.size() > 0 ){
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations){
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (user1 != null){
            user.setUserId(id);
            UserDatabase.updateUser(id, user);
            return Response.ok(user).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/addUser")
    public Response createUser(User user) throws Exception {
        Set<ConstraintViolation<User>> violations = validator.v;

        User user1 = UserDatabase.getUser(user.getUserId());
        if(violations.size() > 0 ){
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (user1 == null) {
            UserDatabase.createUser(user.getUserId(), user);
            return Response.ok("User has been created! ").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("User ID Already Exists, Try a new ID! ").build();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Integer id) {
        User user = UserDatabase.getUser(id);
        if (user != null){
            UserDatabase.deleteUser(id);
            return Response.ok().build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
