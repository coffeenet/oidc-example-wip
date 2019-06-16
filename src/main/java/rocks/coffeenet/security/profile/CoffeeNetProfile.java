package rocks.coffeenet.security.profile;

import java.net.URL;


/**
 * A general interface describing profile information on an authenticated actor in the CoffeeNet platform.
 *
 * <p>While this contains convenient methods for accessing information like the username, full name or avatar URLs,
 * these are not meant to be in any way identifying. For example {@link #getHumanReadableName()} &amp;
 * {@link #getName()} could both return a representation that anonymizes this principal.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface CoffeeNetProfile {

    /**
     * The name of this CoffeeNet profile. This MUST NOT return {@code null}.
     */
    String getName();


    /**
     * The human readable name of this CoffeeNet profile. This MAY return {@code null}.
     */
    String getHumanReadableName();


    /**
     * An URL to a resource representation of this CoffeeNet profile. This MAY return {@code null}.
     */
    URL getProfileURL();


    /**
     * An URL to an image of this CoffeeNet profile. This MAY return {@code null}.
     */
    URL getPictureURL();


    /**
     * The email address of this CoffeeNet profile. This MAY return {@code null}.
     */
    String getEmail();
}
