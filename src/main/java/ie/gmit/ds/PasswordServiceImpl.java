package ie.gmit.ds;

import java.util.logging.Logger;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import javax.print.attribute.HashPrintRequestAttributeSet;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase{

    private static final Logger logger = Logger.getLogger(PasswordServiceImpl.class.getName());

    //Variables
    private String password;
    private int userId;
    private char[] passwordChar;
    private byte[] hashedPassword;
    private byte[] salt;
    private boolean validatePassword;

    @Override
    public void validate(validateRequest request, StreamObserver<BoolValue> responseObserver){
        try {
            System.out.println("Doing some Validation: ");
            validatePassword = Passwords.isExpectedPassword(request.getPassword(), request.getSalt(), request.getHashedPassword());
            System.out.println("\n Validation0" + validatePassword);

            if(validatePassword == true){

            }else {

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*   System.out.println("Doing some Hashing: ");
        //Request password from user
        salt = Passwords.getNextSalt();
        password =  Passwords.hash(request.getPassword().toCharArray(), salt);
        //Get userId and Salt
        //Hash Password
        ByteString saltBS = ByteString.copyFrom(salt);
        ByteString hashedPWBS = ByteString.copyFrom(hashedPassword);

        //print info
        System.out.println("Doing some Hashing: ");
        System.out.println("\n Salt: " + saltBS);
        System.out.println("\n Hashed PW:  " + hashedPWBS);

        responseObserver.onNext(hashResponse.newBuilder().setUserId(request.getUserId()).setHashedPassword(hashedPWBS).setSalt(saltBS).build());
        responseObserver.onCompleted();*/
    @Override
    public void hash(hashRequest request, StreamObserver<hashResponse> responseObserver){

        try{
            //Get Salt + Password + userId
            salt = Passwords.getNextSalt();
            password = request.getPassword();
            userId = request.getUserId();
            //Returns Array of chars after converting String into Seq of characters
            passwordChar = password.toCharArray();
            //Hash Password
            hashedPassword = Passwords.hash(passwordChar, salt);

            hashResponse hashReq = hashResponse.newBuilder()
                    .build();





        }catch(RuntimeException e){
            Logger.getLogger("Failed");
        }
    }
}
