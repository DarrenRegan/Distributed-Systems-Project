package ie.gmit.ds;

import io.grpc.stub.StreamObserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class UserDatabase {

    public final static HashMap<Integer, User> usersMap = new HashMap<Integer, User>();
    private static PasswordClient pClient = new PasswordClient("localhost", 50551);

    static {
       // User test1 = new User(1, "Test", "darren123@gmail.com", "123darren123");
       // User test2 = new User(2, "Test2", "john123@gmail.com", "123john123");
        usersMap.put(1, new User(1, "Darren", "123darren123"));
        usersMap.put(2, new User(1, "John", "123John123"));
    }

    public static List<User> getUsers(){
        return new ArrayList<User>((Collection<? extends User>) usersMap.values());
    }

    public static User getUser(Integer id){
        return usersMap.get(id);
    }

    public static void createUser(final Integer id, final User user){

        hashRequest HashRequest = hashRequest.newBuilder()
                .setUserId(id)
                .setPassword(user.getPassword())
                .build();
        try{
            StreamObserver<hashResponse> responseObserver = new StreamObserver<hashResponse>() {
                User newUser;

                @Override
                public void onNext(hashResponse hashRes) {
                    newUser = new User(user.getUserId(), user.getUserName(), user.getEmail(), user.getPassword(), user.getHashedPassword(), user.getSalt());
                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onCompleted() {
                    usersMap.put(id, newUser);
                }
            };
            pClient.hashPassword(HashRequest, responseObserver);
        }finally {
        }
    }

    public static void updateUser(Integer id, User user){
        usersMap.put(id, user);
    }

    public static void deleteUser(Integer id){
        usersMap.remove(id);
    }

    public static boolean login(Integer id, User user){
        boolean valPass = false;

        try{
            User user1 = getUser(id);
            validateRequest valReq = validateRequest.newBuilder()
                    .setPassword(user.getPassword())
                    .setHashedPassword(user1.getHashedPassword())
                    .setSalt(user1.getSalt())
                    .build();

            valPass = pClient.validatePassword(valReq);

            return valPass;
        } finally {

        }
    }

}
