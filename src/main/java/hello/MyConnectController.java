package hello;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

/**
 * Created by asoule on 12/09/17.
 */
@Controller
@RequestMapping({"/connect"})
public class MyConnectController extends ConnectController {
    public MyConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
        return super.connectionStatus(providerId,request,model);
    }

    @Override
    protected String connectedView(String providerId) {
        return "redirect:/facebook";
    }
}
