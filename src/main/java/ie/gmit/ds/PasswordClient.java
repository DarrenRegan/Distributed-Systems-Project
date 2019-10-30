package ie.gmit.ds;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.BoolValue;
import io.grpc.StatusRuntimeException;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {
    private static final Logger logger = Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncUserService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    /** Construct client for accessing PasswordServer server using the existing channel. */
    public PasswordClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncUserService = PasswordServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void hashPassword(hashRequest hashReq) {
        //Receive Info
        logger.info("Hash Password: " + hashReq);
        hashResponse hashResponse = null;

        //Try to Hash the password given
        try{
            hashResponse = syncPasswordService.hash(hashReq);
            logger.info(("Hash Password: " + hashResponse.getHashedPassword()));
        }catch (RuntimeException error){
            System.out.println("Failed");
            return;
        }
    }

    private void validatePassword(){
    }

    public static void main(String[] args) throws Exception {
        //Localhost
        PasswordClient client = new PasswordClient("localhost", 50051);
        //My IP Address 192.168.0.116
        //PasswordClient client = new PasswordClient("192.168.0.116", 50051);
        Scanner console = new Scanner(System.in);  // Create a Scanner object

        //int userId = 1;
        //String password = "MyNameIsDarrenReganTest";

        //Add User input
        System.out.print("\n Enter ID: ");
        int userId = console.nextInt();
        System.out.print("\n Enter Password ");
        String password = console.next();

        hashRequest hashReq = hashRequest.newBuilder()
                .setPassword(password)
                .setUserId(userId)
                .build();

        try{
            client.hashPassword(hashReq);
           // client.validatePassword();
        }finally {
            Thread.currentThread().join();
        }
    }
}
