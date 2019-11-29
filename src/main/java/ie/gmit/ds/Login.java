package ie.gmit.ds;

import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;

import java.util.ArrayList;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;


@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    private final Validator validator;

    public Login (Validator validator){
        this.validator = validator;
    }

    @POST
    public Response Login (User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User user1;

        user1 = UserDatabase.getUser(user.getUserId());

        if (violations.size() > 0 ){
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations){
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        boolean validatePass = UserDatabase.login(user.getUserId(), user);

        if (validatePass){
            return Response.ok("Successfully logged in ").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Incorrect Password").build();
        }
    }
}
