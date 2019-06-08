package rocks.coffeenet.security.user.oidc;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import rocks.coffeenet.security.user.CoffeeNetUser;

import java.io.Serializable;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collection;
import java.util.Map;


/**
 * An {@link OidcUser} wrapper implementing the {@link CoffeeNetUser} interface.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetOidcUser implements OidcUser, CoffeeNetUser, Serializable {

    private static final long serialVersionUID = 7969823708079378742L;

    private OidcUser delegate;

    CoffeeNetOidcUser(OidcUser delegate) {

        this.delegate = delegate;
    }

    @Override
    public Map<String, Object> getClaims() {

        return delegate.getClaims();
    }


    @Override
    public OidcUserInfo getUserInfo() {

        return delegate.getUserInfo();
    }


    @Override
    public OidcIdToken getIdToken() {

        return delegate.getIdToken();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return delegate.getAuthorities();
    }


    @Override
    public Map<String, Object> getAttributes() {

        return delegate.getAttributes();
    }


    @Override
    public String getName() {

        return delegate.getName();
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

        return getPreferredUsername();
    }


    @Override
    public String getEmail() {

        return delegate.getEmail();
    }
}
