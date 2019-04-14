package rocks.coffeenet.security.user.oidc;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import org.springframework.util.StringUtils;


/**
 * An enhanced {@link OidcUserService} that produces {@link CoffeeNetOidcUser} instances.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        CoffeeNetOidcUser coffeeNetOidcUser;

        if (StringUtils.hasText(userNameAttributeName)) {
            coffeeNetOidcUser = new CoffeeNetOidcUser(oidcUser.getAuthorities(), userRequest.getIdToken(),
                    oidcUser.getUserInfo(), userNameAttributeName);
        } else {
            coffeeNetOidcUser = new CoffeeNetOidcUser(oidcUser.getAuthorities(), userRequest.getIdToken(),
                    oidcUser.getUserInfo());
        }

        return coffeeNetOidcUser;
    }
}
