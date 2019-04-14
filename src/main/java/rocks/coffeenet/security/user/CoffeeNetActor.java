package rocks.coffeenet.security.user;

import org.springframework.security.core.GrantedAuthority;

import java.net.URL;

import java.util.Collection;


/**
 * A general interface describing profile information on an authenticated actor in the CoffeeNet platform.
 *
 * <p>While this contains convenient methods for accessing information like the username, full name or avatar URLs,
 * these are not meant to be in any way identifying. For example {@link #getHumanReadableName()} &amp;
 * {@link #getName()} could both return a representation that anonymizes this principal.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface CoffeeNetActor {

    /**
     * The name of this CoffeeNet actor. This MUST NOT return {@code null}.
     */
    String getName();


    /**
     * The human readable name of this CoffeeNet actor. This MAY return {@code null}.
     */
    default String getHumanReadableName() {

        return getName();
    }


    /**
     * An URL to a resource representation of this CoffeeNet actor. This MAY return {@code null}.
     */
    URL getProfileURL();


    /**
     * An URL to an image of this CoffeeNet actor. This MAY return {@code null}.
     */
    URL getPictureURL();


    /**
     * Returns the authorities granted to the actor.
     */
    Collection<? extends GrantedAuthority> getAuthorities();
}
