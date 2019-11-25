package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class UserApiApp extends Application<UserAPIConfig> {

    public static void main(String[] args) throws Exception {
        new UserApiApp().run(args);
    }

    @Override
    public void run(UserAPIConfig userAPIConfig, Environment environment) throws Exception {
        //Call userapiresouce
        //Do a Health Check

    }
}
