package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;


/**
 * {@link EnableAutoConfiguration} for automatic OIDC web security configuration in the presence of configured OIDC
 * providers.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@ConditionalOnClass({ EnableWebSecurity.class, ClientRegistration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Conditional(ClientsConfiguredCondition.class)
public class OidcSecurityAutoConfiguration {

    @Configuration
    static class GlobalSecurityConfigurerConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OidcSecurityConfigurer coffeeNetOidcConfigurer() {

            return new OidcSecurityConfigurer();
        }
    }

    @Configuration
    @ConditionalOnMissingBean(CoffeeNetOidcProfileMapper.class)
    static class CoffeenetOidcProfileMapperConfiguration {

        @Bean
        public CoffeeNetOidcProfileMapper coffeeNetOidcProfileMapper() {

            return new CoffeeNetOidcProfileMapper();
        }
    }
}
