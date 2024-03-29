package ie.gmit.ds;

import com.google.protobuf.Api;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import javax.xml.validation.Validator;

public class UserApiApp extends Application<UserAPIConfig> {

    public static void main(String[] args) throws Exception {
        new UserApiApp().run(args);
    }

    @Override
    public void run(UserAPIConfig userAPIConfig, Environment environment) throws Exception {

        final Resource resource = new Resource(environment.getValidator());
        final Login resource1 = new Login(environment.getValidator());
        final ApiHealthCheck healthCheck = new ApiHealthCheck();

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
        environment.jersey().register(resource1);
    }
}
