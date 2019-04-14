package rocks.coffeenet.security.user.oidc;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import rocks.coffeenet.security.user.CoffeeNetUser;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collection;


/**
 * An {@link OidcUser} implementing the {@link CoffeeNetUser} interface.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetOidcUser extends DefaultOidcUser implements CoffeeNetUser {

    public CoffeeNetOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
        OidcUserInfo userInfo) {

        super(authorities, idToken, userInfo);
    }


    public CoffeeNetOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
        OidcUserInfo userInfo, String nameAttributeKey) {

        super(authorities, idToken, userInfo, nameAttributeKey);
    }

    @Override
    public String getHumanReadableName() {

        return getFullName();
    }


    @Override
    public URL getProfileURL() {

        try {
            return new URL(getProfile());
        } catch (MalformedURLException e) {
            return null;
        }
    }


    @Override
    public URL getPictureURL() {

        try {
            return new URL(getPicture());
        } catch (MalformedURLException e) {
            return null;
        }
    }


    @Override
    public String getUsername() {

        return super.getPreferredUsername();
    }


    @Override
    public String getEmail() {

        return super.getEmail();
    }
}
