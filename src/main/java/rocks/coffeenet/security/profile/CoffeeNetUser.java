package rocks.coffeenet.security.profile;

/**
 * A CoffeeNet actor representing an user.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface CoffeeNetUser extends CoffeeNetActor {

    /**
     * The preferred user name of this CoffeeNet user. This MUST NOT return {@code null}.
     */
    String getUsername();


    /**
     * The email address of this CoffeeNet user. This MAY return {@code null}.
     */
    String getEmail();
}
