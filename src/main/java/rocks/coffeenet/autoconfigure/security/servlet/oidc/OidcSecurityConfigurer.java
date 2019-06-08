package rocks.coffeenet.autoconfigure.security.servlet.oidc;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import rocks.coffeenet.autoconfigure.security.servlet.GlobalCoffeeNetSecurityConfigurer;


/**
 * Apply a custom {@link OidcUserService} if present.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class OidcSecurityConfigurer extends GlobalCoffeeNetSecurityConfigurer<OidcSecurityConfigurer, HttpSecurity> {

    private OidcUserService userService;

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void customize(HttpSecurity http) throws Exception {

        // We need to run before the OAuth2 login configurer
        OAuth2LoginConfigurer oauth2Login = http.removeConfigurer(OAuth2LoginConfigurer.class);

        // re-apply existing or simply enable
        if (oauth2Login != null) {
            http.apply(oauth2Login);
        } else {
            http.oauth2Login();
        }
    }


    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void init(HttpSecurity http) throws Exception {

        OAuth2LoginConfigurer oauth2Login = http.getConfigurer(OAuth2LoginConfigurer.class);

        // Register custom OIDC user service if available
        if (oauth2Login != null && userService != null) {
            oauth2Login.userInfoEndpoint().oidcUserService(userService);
        }
    }


    void setUserService(OidcUserService userService) {

        this.userService = userService;
    }
}
