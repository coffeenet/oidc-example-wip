package rocks.coffeenet.autoconfigure.security.profile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rocks.coffeenet.security.profile.PrincipalProfileMapper;
import rocks.coffeenet.security.profile.PrincipalProfileMapperManager;

import java.util.List;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
public class CoffeeNetProfileAutoConfiguration {

    @Configuration
    @ConditionalOnMissingBean(PrincipalProfileMapperManager.class)
    static class PrincipalProfileMapperManagerConfiguration {

        @Bean
        @Primary
        @ConditionalOnBean(PrincipalProfileMapper.class)
        PrincipalProfileMapper principalProfileMapperManager(List<PrincipalProfileMapper> mappers) {

            return new PrincipalProfileMapperManager(mappers);
        }
    }

    @Configuration
    @ConditionalOnBean(PrincipalProfileMapper.class)
    static class PrincipalProfileArgumentResolverConfiguration implements WebMvcConfigurer {

        private final PrincipalProfileMapper principalProfileMapper;

        PrincipalProfileArgumentResolverConfiguration(PrincipalProfileMapper principalProfileMapper) {

            this.principalProfileMapper = principalProfileMapper;
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

            CoffeeNetProfileHandlerMethodArgumentResolver resolver;

            resolver = new CoffeeNetProfileHandlerMethodArgumentResolver(principalProfileMapper);
            resolvers.add(0, resolver);
        }
    }
}
