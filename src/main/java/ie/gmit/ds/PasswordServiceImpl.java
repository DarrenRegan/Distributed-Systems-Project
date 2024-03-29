package ie.gmit.ds;

import java.util.logging.Logger;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.Status;
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
            //Returns Array of chars after converting String into Seq of characters
            passwordChar = password.toCharArray();
            System.out.println("Doing some Validation: ");

            //Get request Password Salt and Hashed password to validate
            validatePassword = Passwords.isExpectedPassword(request.getPassword(), request.getSalt(), request.getHashedPassword());
            System.out.println("\n Validation: " + validatePassword);

            if(validatePassword == true){
                System.out.println("Password is Valid ");
                responseObserver.onNext(BoolValue.newBuilder().setValue(true).build());
            }else {
                System.out.println("Password is Invalid ");
                responseObserver.onNext(BoolValue.newBuilder().setValue(false).build());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }

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
                    .setSalt(ByteString.copyFrom(salt))
                    .setHashedPassword(ByteString.copyFrom(hashedPassword))
                    .setUserId(userId)
                    .build();

            //Validation
            validatePassword = Passwords.isExpectedPassword(passwordChar, salt, hashedPassword);
            if(validatePassword == true){
                System.out.println(" --- " + "ID: " + userId + " --- " + "Password: " + password + " --- " +  "HashPassword: " + hashedPassword + " --- " );
                System.out.println(" --- " + "Validated? " + validatePassword + " --- ");
            }else {
                System.out.println("Password is Invalid ");
            }
            responseObserver.onNext(hashReq);

        }catch(RuntimeException e){
            responseObserver.onError(Status.fromCode(Status.Code.FAILED_PRECONDITION)
                .withCause(e)
                .withDescription("BAD PASSWORD")
                .asException());
        }
        responseObserver.onCompleted();
    }
}
