package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private UserService userService;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository, UsersConnectionRepository userConnectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.usersConnectionRepository = userConnectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model , Principal principal, HttpServletRequest request) {
    	if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        FacebookConnectionFactory fcf = new FacebookConnectionFactory(
                "1904974643090279",
                "37bd9bc02b21495cd004647db24c42e7");
        //this is the important bit
        fcf.setScope("public_profile,email");


        OAuth2Operations oauthOperations = fcf.getOAuthOperations();
        try {
            AccessGrant accessGrant = oauthOperations.exchangeForAccess("4906680a14644220a858a9e73dac5862", "http://localhost:8080/", null);
            String accessToken = accessGrant.getAccessToken();
            Connection<Facebook> connection = fcf.createConnection(accessGrant);

            UserProfile profile = connection.fetchUserProfile();
//        ConnectionKey connectionKey = connection.getKey();
//        String usn = connectionKey.getProviderUserId();
//        String email2 = profile.getEmail();
        }
        catch (Exception e){

        }



        String [] fields = { "id","name","birthday","email","location","hometown","gender","first_name","last_name","picture"};
        User user = facebook.fetchObject("me", User.class, fields);
        String name=user.getName();
        String birthday=user.getBirthday();
        String email=user.getEmail();
        String gender=user.getGender();
        String firstname=user.getFirstName();
        String lastname=user.getLastName();

        hello.User n = new hello.User();
        n.setName(name);
        n.setEmail(email);
        userService.save(n);

        Object o = user.getExtraData().get("picture");
        LinkedHashMap linkedHashMap = (LinkedHashMap) o;
        String picture = ((LinkedHashMap) linkedHashMap.get("data")).get("url").toString();
        byte[] photo = facebook.userOperations().getUserProfileImage();

        model.addAttribute("name",name );
        model.addAttribute("birthday",birthday );
        model.addAttribute("email",email );
        model.addAttribute("gender",gender);
        model.addAttribute("firstname",firstname);
        model.addAttribute("lastname",lastname);
        model.addAttribute("picture", picture);
        model.addAttribute("facebookProfile", facebook.fetchObject("me", User.class, fields));
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "hello";
    }

    @GetMapping
    @RequestMapping("/connected/facebook")
    public String helloFacebook2(Model model , Principal principal, HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return "connect/facebookConnected";
    }

}
