package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import rocks.coffeenet.security.user.oidc.CoffeeNetOidcUserService;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class OidcSecurityAutoConfigurationTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner = new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(
                        SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class,
                        CoffeeNetSecurityAutoConfiguration.class, OidcSecurityAutoConfiguration.class))
                .withPropertyValues("spring.security.oauth2.client.registration.example.client-id=example",
                        "spring.security.oauth2.client.registration.example.client-secret=example",
                        "spring.security.oauth2.client.registration.example.provider=example",
                        "spring.security.oauth2.client.provider.example.issuer-uri=http://localhost/issuer");
    }


    @Test
    @DisplayName("should configure a custom OIDC user service if requirements are met")
    void customOidcUserService() {

        contextRunner.run((context -> assertThat(context).hasSingleBean(CoffeeNetOidcUserService.class)));
    }


    @Test
    @DisplayName("should not configure a custom OIDC user service if requirements aren't met")
    void noCustomOidcUserService() {

        contextRunner.withClassLoader(new FilteredClassLoader("org.springframework.security.oauth2.jwt.JwtDecoder"))
            .run((context -> assertThat(context).doesNotHaveBean(CoffeeNetOidcUserService.class)));
    }
}
