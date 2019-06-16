package rocks.coffeenet.security.profile;

import java.security.Principal;

import java.util.List;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class PrincipalProfileMapperManager implements PrincipalProfileMapper {

    private final List<PrincipalProfileMapper> mappers;

    public PrincipalProfileMapperManager(List<PrincipalProfileMapper> mappers) {

        if (mappers == null) {
            throw new IllegalArgumentException("list of profile mappers must not be null.");
        }

        if (mappers.isEmpty()) {
            throw new IllegalArgumentException("list of profile mappers must not be empty.");
        }

        this.mappers = mappers;
    }

    @Override
    public CoffeeNetProfile mapPrincipal(Principal principal) {

        Class<? extends Principal> principalClass = principal.getClass();

        CoffeeNetProfile profile = null;

        for (PrincipalProfileMapper mapper : mappers) {
            if (!mapper.supports(principalClass)) {
                continue;
            }

            profile = mapper.mapPrincipal(principal);

            if (profile != null) {
                break;
            }
        }

        return profile;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return true;
    }
}
