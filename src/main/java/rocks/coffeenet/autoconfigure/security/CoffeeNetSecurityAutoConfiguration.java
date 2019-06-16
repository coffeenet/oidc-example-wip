package rocks.coffeenet.autoconfigure.security;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

import rocks.coffeenet.autoconfigure.security.servlet.CoffeeNetWebSecurityConfiguration;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security in CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@Import(CoffeeNetWebSecurityConfiguration.class)
public class CoffeeNetSecurityAutoConfiguration {
}
