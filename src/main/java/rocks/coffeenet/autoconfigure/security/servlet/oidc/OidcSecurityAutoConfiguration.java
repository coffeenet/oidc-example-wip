package rocks.coffeenet.autoconfigure.security.servlet.oidc;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import rocks.coffeenet.security.user.CoffeeNetUser;
import rocks.coffeenet.security.user.oidc.CoffeeNetOidcUserService;


/**
 * {@link EnableAutoConfiguration} for custom OIDC user services in the presence of configured OIDC providers.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
@ConditionalOnClass({ EnableWebSecurity.class, ClientRegistration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Conditional(ClientsConfiguredCondition.class)
public class OidcSecurityAutoConfiguration {

    @Configuration
    @ConditionalOnClass(name = "org.springframework.security.oauth2.jwt.JwtDecoder")
    static class UserServiceConfiguration {

        /**
         * Register a custom OIDC user service to provide {@link CoffeeNetUser} based implementations of the OIDC user.
         */
        @Bean
        @ConditionalOnClass(OidcUserService.class)
        @ConditionalOnMissingBean(OidcUserService.class)
        public CoffeeNetOidcUserService coffeeNetOidcUserService() {

            return new CoffeeNetOidcUserService();
        }
    }

    @Configuration
    static class GlobalSecurityConfigurerConfiguration {

        private OidcUserService userService;

        @Bean
        @ConditionalOnMissingBean
        public OidcSecurityConfigurer coffeeNetOidcConfigurer() {

            OidcSecurityConfigurer configurer = new OidcSecurityConfigurer();

            if (userService != null) {
                configurer.setUserService(userService);
            }

            return configurer;
        }


        @Autowired(required = false)
        public void setUserService(OidcUserService userService) {

            this.userService = userService;
        }
    }
}
