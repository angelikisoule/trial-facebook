package hello;

import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

/**
 * Created by asoule on 11/09/17.
 */
public class MyAuthorizationCodeAccessTokenProvider extends OAuth2ClientAuthenticationProcessingFilter{

    public MyAuthorizationCodeAccessTokenProvider(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        // TODO Auto-generated constructor stub
    }

}