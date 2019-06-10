package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/**
 * A {@link AbstractHttpConfigurer} marker class for security configurers. It will be added unconditionally, as long as
 * it is registered as a Spring Bean. It is used to automatically enable security features in CoffeeNet enabled
 * applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SuppressWarnings("squid:S00118") // This is internal framework code, we don't care for the naming convention here.
public abstract class GlobalCoffeeNetSecurityConfigurer<T extends GlobalCoffeeNetSecurityConfigurer<T>>
    extends AbstractHttpConfigurer<T, HttpSecurity> {
}
