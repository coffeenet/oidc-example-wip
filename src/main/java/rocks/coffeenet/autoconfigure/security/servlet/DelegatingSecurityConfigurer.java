package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.context.ApplicationContext;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static rocks.coffeenet.autoconfigure.security.servlet.GlobalCoffeeNetSecurityConfigurer.CoffeeNetSecurityException;


/**
 * Load {@link GlobalCoffeeNetSecurityConfigurer} instances registered in the application context and apply them to
 * {@link WebSecurityConfigurerAdapter} instances. If you do not want this, use
 * {@link WebSecurityConfigurerAdapter(boolean)} with {@code true} to disable defaults.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class DelegatingSecurityConfigurer<H extends HttpSecurityBuilder<H>>
    extends AbstractHttpConfigurer<DelegatingSecurityConfigurer<H>, H> {

    /*
     * HACK: since we chain into the defaults of {@link WebSecurityConfigurerAdapter} and want to apply customizations
     * before the potentially overridden {@link WebSecurityConfigurerAdapter#configure(HttpSecurity)} runs, we exploit
     * the fact, that {@link SecurityConfigurerAdapter#setBuilder(SecurityBuilder)} is called right before {@code this}
     * is added to the list of configurers. It's not very nice, but we will eventually get access to OIDC configurers,
     * that won't need to run before other default configurers.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void setBuilder(H builder) {

        super.setBuilder(builder);

        if (builder instanceof HttpSecurity) {
            HttpSecurity http = (HttpSecurity) builder;

            for (GlobalCoffeeNetSecurityConfigurer configurer : getAdditionalConfigurers(http)) {
                apply(http, configurer);
            }
        }
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void apply(HttpSecurity http, GlobalCoffeeNetSecurityConfigurer configurer) {

        try {
            http.apply(configurer).customize(http);
        } catch (Exception e) {
            throw new CoffeeNetSecurityException(e);
        }
    }


    private List<GlobalCoffeeNetSecurityConfigurer> getAdditionalConfigurers(HttpSecurity http) {

        ApplicationContext context = http.getSharedObject(ApplicationContext.class);

        if (context == null) {
            return Collections.emptyList();
        }

        return context.getBeansOfType(GlobalCoffeeNetSecurityConfigurer.class)
            .values()
            .stream()
            .sorted(AnnotationAwareOrderComparator.INSTANCE)
            .collect(Collectors.toList());
    }
}
