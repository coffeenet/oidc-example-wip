package rocks.coffeenet.security.profile;

import java.security.Principal;


/**
 * A mapping interface to obtain a {@link CoffeeNetActor} profile associated with the provided {@link Principal}.
 * Implementations are meant to decouple the authentication of an user from a rich profile. This also allows handling
 * of anonymous authentication for profile representation of an anonymous user or authentication via multiple methods,
 * pointing to the same user profile.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@FunctionalInterface
public interface PrincipalActorMapper {

    /**
     * Attempts to map the passed {@link Principal} object, returning a {@link CoffeeNetActor} if successful.
     *
     * @param  principal  the authenticated principal object
     *
     * @return  a fully populated actor profile. MAY return {@code null} if the {@link PrincipalActorMapper} is unable
     *          to determine a profile. This can be either because the passed {@link Principal} object is not supported
     *          or the contained information is insufficient to extract a profile.
     */
    <O extends CoffeeNetActor> O mapPrincipal(Principal principal);


    /**
     * Determine if the passed type is supported by this mapper. This
     *
     * @param  clazz
     *
     * @return
     */
    default boolean supports(Class<?> clazz) {

        return Principal.class.isAssignableFrom(clazz);
    }
}
