package ie.gmit.ds;

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

    

}
