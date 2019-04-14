package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/**
 * A {@link SecurityConfigurer} that is added in {@link WebSecurityConfigurerAdapter} unconditionally, as long as it is
 * registered as a Spring Bean. It is used to automatically enable security features in CoffeeNet enabled applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SuppressWarnings("squid:S00118") // This is internal framework code, we don't care for the naming convention here.
public abstract class GlobalCoffeeNetSecurityConfigurer<T extends GlobalCoffeeNetSecurityConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
    extends AbstractHttpConfigurer<T, B> {

    @SuppressWarnings("squid:S00112") // This could throw anything, better be prepared…
    protected void customize(B builder) throws Exception {
    }

    static class CoffeeNetSecurityException extends RuntimeException {

        private static final long serialVersionUID = -8619258996859741729L;

        CoffeeNetSecurityException(Throwable cause) {

            super(cause);
        }
    }
}
