package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import rocks.coffeenet.autoconfigure.security.servlet.GlobalCoffeeNetSecurityConfigurer;


/**
 * Apply a custom {@link OidcUserService} if present.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class OidcSecurityConfigurer extends GlobalCoffeeNetSecurityConfigurer<OidcSecurityConfigurer> {

    @Override
    public void init(HttpSecurity http) throws Exception {

        http.oauth2Login().and().oauth2Client();
    }
}
