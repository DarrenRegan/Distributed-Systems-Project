package ie.gmit.ds;

import java.util.logging.Logger;
import io.grpc.stub.StreamObserver;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase{

    private static final Logger logger = Logger.getLogger(PasswordServiceImpl.class.getName());

    //Variables
    String password;
    int userId;
    char passwordChar;
    byte hashedPassword;
    byte salt;
    boolean validatePassword;

    @Override
    public void validate(){

    }

    @Override
    public void hash(){

        //Request password from user
        //Get userId and Salt

        //Hash Password

        //print info

    }
}
