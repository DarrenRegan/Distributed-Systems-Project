package ie.gmit.ds;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.BoolValue;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {
    private static final Logger logger = Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    //private final PasswordServiceGrpc.PasswordServiceStub asyncUserService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    /** Construct client for accessing HelloWorld server using the existing channel. */
    public PasswordClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void hashPassword(hashRequest hashReq) {
        //Receive Info

        //Try to Hash the password given

    }

    public static void main(String[] args) throws Exception {
        PasswordClient client = new PasswordClient("localhost", 50051);
    }
}
