package rocks.coffeenet.autoconfigure.security.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class CoffeeNetWebSecurityConfigurationTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                    SecurityFilterAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class));
    }


    @Test
    @DisplayName("should configure default security adapter, if none is present")
    void defaultConfigurerAdapter() {

        contextRunner.run((context -> {
                assertThat(context).hasSingleBean(CoffeeNetWebSecurityConfiguration.DefaultConfigurerAdapter.class);
            }));
    }


    @Test
    @DisplayName("should not configure default security adapter, if one is present")
    void customConfigurerAdapter() {

        contextRunner.withUserConfiguration(CustomAdapter.class).run((context -> {
                assertThat(context).doesNotHaveBean(CoffeeNetWebSecurityConfiguration.DefaultConfigurerAdapter.class);
            }));
    }

    @Configuration
    static class CustomAdapter extends WebSecurityConfigurerAdapter {
    }
}
