package rocks.coffeenet.security.profile;

import java.security.Principal;

import java.util.List;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class PrincipalActorMapperManager implements PrincipalActorMapper {

    private final List<PrincipalActorMapper> mappers;

    public PrincipalActorMapperManager(List<PrincipalActorMapper> mappers) {

        if (mappers == null) {
            throw new IllegalArgumentException("list of actor mapper must not be null.");
        }

        if (mappers.isEmpty()) {
            throw new IllegalArgumentException("list of actor mapper must not be empty.");
        }

        this.mappers = mappers;
    }

    @Override
    public <O extends CoffeeNetActor> O mapPrincipal(Principal principal) {

        Class<? extends Principal> principalClass = principal.getClass();

        CoffeeNetActor actor = null;

        for (PrincipalActorMapper mapper : mappers) {
            if (!mapper.supports(principalClass)) {
                continue;
            }

            actor = mapper.mapPrincipal(principal);

            if (actor != null) {
                break;
            }
        }

        return (O) actor;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return true;
    }
}
