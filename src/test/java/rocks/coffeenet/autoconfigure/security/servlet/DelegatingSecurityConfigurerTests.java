package rocks.coffeenet.autoconfigure.security.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class DelegatingSecurityConfigurerTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                    SecurityFilterAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class));
    }


    @Test
    @DisplayName("should run custom CoffeeNet security configurer when bean present")
    void customSecurityConfigurer() {

        contextRunner.withUserConfiguration(CustomConfigurer.class).run((context) ->
                assertThat(context).getBean(CustomConfigurer.class).isNotNull().satisfies(configurer ->
                        assertThat(configurer.customized).isTrue()));
    }

    @Configuration
    static class CustomConfigurer extends GlobalCoffeeNetSecurityConfigurer<CustomConfigurer> {

        private boolean customized = false;

        @Override
        public void init(HttpSecurity builder) throws Exception {

            customized = true;
        }
    }
}
