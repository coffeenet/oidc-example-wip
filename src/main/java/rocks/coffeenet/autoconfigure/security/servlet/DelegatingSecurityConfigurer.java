package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.context.ApplicationContext;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Load {@link GlobalCoffeeNetSecurityConfigurer} instances registered in the application context and apply them to
 * {@link WebSecurityConfigurerAdapter} instances. If you do not want this, use
 * {@link WebSecurityConfigurerAdapter(boolean)} with {@code true} to disable defaults.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class DelegatingSecurityConfigurer extends AbstractHttpConfigurer<DelegatingSecurityConfigurer, HttpSecurity> {

    private List<GlobalCoffeeNetSecurityConfigurer> delegates = Collections.emptyList();

    @Override
    public void init(HttpSecurity http) throws Exception {

        for (GlobalCoffeeNetSecurityConfigurer<?> configurer : delegates) {
            configurer.init(http);
        }
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        for (GlobalCoffeeNetSecurityConfigurer<?> configurer : delegates) {
            configurer.configure(http);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public void setBuilder(HttpSecurity http) {

        super.setBuilder(http);

        synchronized (this) {
            ApplicationContext context = http.getSharedObject(ApplicationContext.class);
            delegates = context.getBeansOfType(GlobalCoffeeNetSecurityConfigurer.class)
                    .values()
                    .stream()
                    .sorted(AnnotationAwareOrderComparator.INSTANCE).peek(configurer -> configurer.setBuilder(http))
                    .collect(Collectors.toList());
        }
    }
}
