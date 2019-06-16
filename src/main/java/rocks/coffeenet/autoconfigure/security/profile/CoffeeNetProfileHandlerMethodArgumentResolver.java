package rocks.coffeenet.autoconfigure.security.profile;

import org.springframework.core.MethodParameter;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import rocks.coffeenet.security.profile.CoffeeNetProfile;
import rocks.coffeenet.security.profile.PrincipalProfileMapper;

import java.security.Principal;


/**
 * Allow injection of the current {@link CoffeeNetProfile} into {@link org.springframework.stereotype.Controller}
 * methods.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetProfileHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final PrincipalProfileMapper principalProfileMapper;

    public CoffeeNetProfileHandlerMethodArgumentResolver(PrincipalProfileMapper principalProfileMapper) {

        this.principalProfileMapper = principalProfileMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return CoffeeNetProfile.class.equals(parameter.getParameterType());
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Principal principal = webRequest.getUserPrincipal();

        return principalProfileMapper.mapPrincipal(principal);
    }
}
