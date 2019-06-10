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

    private OidcUserService userService;

    @Override
    public void init(HttpSecurity http) throws Exception {

        //J-
        //@formatted:off
        if (userService != null) {
            http.oauth2Login()
                    .userInfoEndpoint().oidcUserService(userService)
                    .and()
                .and()
                .oauth2Client()
                .and()
                .formLogin().disable()
                .httpBasic().disable();
        } else {
            http.oauth2Login()
                 .and()
                 .oauth2Client()
                 .and()
                 .formLogin().disable()
                 .httpBasic().disable();
        }
        //@formatted:on
        //J+
    }


    void setUserService(OidcUserService userService) {

        this.userService = userService;
    }
}
