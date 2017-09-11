package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * Created by asoule on 11/09/17.
 */
@Component
public class FacebookConnectionSignup implements ConnectionSignUp{

    @Autowired
    private UserService userservice;

    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setName(connection.getDisplayName());
        userservice.save(user);
        return connection.getDisplayName();
    }

}
