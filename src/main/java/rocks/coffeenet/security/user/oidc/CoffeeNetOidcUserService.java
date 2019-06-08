package rocks.coffeenet.security.user.oidc;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import rocks.coffeenet.security.user.CoffeeNetUser;


/**
 * An enhanced {@link OidcUserService} that wraps {@link OidcUser} to support the {@link CoffeeNetUser} interface.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        return new CoffeeNetOidcUser(oidcUser);
    }
}
